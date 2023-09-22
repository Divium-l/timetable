package me.divium.timetable.scrapper.scrappers

import me.divium.timetable.scrapper.Scrapper
import me.divium.timetable.scrapper.ParserException
import me.divium.timetable.scrapper.model.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.IOException

/**
 * HTML scrapper for RUT (MIIT) timetable for mobile website
 */
class HtmlRutMobileGroupTimetableScrapper(var url: String) : Scrapper {
    var timetable: Timetable? = null
        private set

    override fun scrape() {
        try {
            parseTimetable(url)
        } catch (e: IOException) {
            throw ParserException("Couldn't connect. Original exception message {${e.message}}")
        }

        this.timetable = parseTimetable(url)
    }

    /**
     * Parses timetable requested by URL
     * @param url Timetable link
     */
    private fun parseTimetable(url: String): Timetable {
        val document = Jsoup.connect(url).get()
        val groupName = document
            .selectFirst(".page-header-name__title")
            ?.text()
            ?.replace("Расписание учебной группы", "")
            ?.trim() ?: throw ParserException("Couldn't parse group name")
        val weekElements = parseWeeks(document)
        val weekList: MutableList<Week> = mutableListOf()
        for (i in weekElements.indices) {
            val dayList: MutableList<Day> = mutableListOf()
            val dayElements = weekElements[i].select("div .info-block")
            for (dayElement in dayElements) {
                val day = parseDay(dayElement)
                dayList.add(day)
            }
            val week = Week("Week ${i + 1}", dayList)
            weekList.add(week)
        }

        return Timetable(groupName, weekList)
    }

    /**
     * Parses week
     * @param
     */
    private fun parseWeeks(document: Document): List<Element> {
        val week1 = document.selectFirst("#week-1")
        val week2 = document.selectFirst("#week-2")

        if (week1 == null || week2 == null)
            throw ParserException("Couldn't parse weeks")

        return listOf(week1, week2)
    }

    /**
     * Parses timetable day
     * @param day Div containing day
     * @return Parsed day
     */
    private fun parseDay(day: Element): Day {
        val lessonList: MutableList<Lesson> = mutableListOf()

        val header = day.selectFirst(".info-block__header-text") ?: throw ParserException("Day header is null")
        val dayOfWeek = header.text().trim()

        val lessons = day.select(".timetable__list-timeslot")
        for (lesson in lessons) {
            lessonList.addAll(parseLesson(lesson))
        }

        return Day(
            dayOfWeek = dayOfWeek,
            lessons = lessonList
        )
    }

    /**
     * Parses lesson
     * @param lesson Div containing lesson
     * @return List of lessons. Typically, list consists of one lesson, but in some cases there could be more
     */
    private fun parseLesson(lesson: Element): List<Lesson> {
        val lessonList: MutableList<Lesson>  = mutableListOf()

        val lessonTimeDiv = lesson.selectFirst(".mb-1") ?: throw ParserException("Couldn't get lesson time")
        val lessonNumber = lessonTimeDiv
            .text()
            .trim()
            .split(",")
            .get(0)
            .replace(Regex("\\D"), "")
            .toByte()
        val lessonTime = lessonTimeDiv
            .text()
            .trim()
            .split(",")
            .get(1)

        val lessonData = lesson.selectFirst(".pl-4") ?: throw ParserException("Couldn't get lesson data")
        val lessonDataTextNodes = lessonData.textNodes().filter { textNode -> textNode.text().isNotBlank() }
        val lessonTypes = lesson.getElementsByClass("timetable__grid-text_gray")
        val lessonInfos = lesson.getElementsByClass("timetable__grid-about")

        assert(
            lessonDataTextNodes.size == lessonTypes.size && lessonTypes.size == lessonInfos.size
        ) { "Count of nested elements must be equal. Got ${lessonDataTextNodes.size}, ${lessonTypes.size}, ${lessonInfos.size}" }

        for (i in lessonDataTextNodes.indices) {
            val name = lessonDataTextNodes[i].text().replace("\"", "").trim()
            val type = lessonTypes[i].text().trim()

            val lessonInfo = parseLessonInfo(lessonInfos[i])

            lessonList.add(
                Lesson(
                    name = name,
                    type = type,
                    number = lessonNumber,
                    time = lessonTime,
                    info = lessonInfo
                )
            )
        }

        return lessonList
    }

    /**
     * Parses available lesson information
     * @param lessonInfo Div containing nested divs with lesson data
     * @return Parsed lesson info
     */
    private fun parseLessonInfo(lessonInfo: Element): LessonInfo {
        val divs = lessonInfo.children().select("div")

        /*val teacher: String? = if (divs.size > 0) divs[0].select("a").attr("title") else null
        val room: String? = if (divs.size > 1) divs[1].text().trim().replace(Regex("\\D"), "") else null
        val group: String? = if (divs.size > 0) divs[2].text().replace("Аудитория", "").trim() else null*/

        return LessonInfo(
            teacher = if (divs.size > 0) divs[0].attr("title") else null,
            room = if (divs.size > 1) divs[1].text().trim().replace(Regex(":?\\d"), "") else null,
            group = if (divs.size > 2) divs[2].text().trim() else null
        )
    }
}