package me.divium.timetable.scrapper.scrappers

import me.divium.timetable.scrapper.lib.UniversityScrapper
import me.divium.timetable.scrapper.exceptions.ParserException
import me.divium.timetable.scrapper.model.group.SFaculty
import me.divium.timetable.scrapper.model.group.SGroup
import me.divium.timetable.scrapper.model.group.SUniversity
import me.divium.timetable.scrapper.model.group.SYear
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


/**
 * Class for parsing departments, groups and their links
 */
class HtmlRutUniversityScrapper(private var url: String) : UniversityScrapper {
    companion object {
        const val UNIVERSITY_NAME = "RUT";
    }

    private var faculties: List<SFaculty> = listOf()

    override fun scrape() {
        faculties = parseAllInstitutes(url)
    }

    override fun getResult(): SUniversity {
        return SUniversity(UNIVERSITY_NAME, faculties)
    }

    /**
     * Starts parsing. Parser all institutes
     * @param url Url to parse
     * @return List of institutes
     */
    private fun parseAllInstitutes(url: String): List<SFaculty> {
        val document = Jsoup.connect(url).get()
        val catalog = document.select("section .timetable-catalog") ?: throw ParserException("Time table catalogue not found")

        val divs = catalog.select(".info-block_collapse")
        val sFaculties = mutableListOf<SFaculty>()
        for (div in divs) {
            val institute = parseInstitute(div)
            sFaculties.add(institute)
        }

        return sFaculties
    }

    /**
     * Parses all groups in institute
     * @param institute Div containing institute
     * @return List of groups
     */
    private fun parseInstitute(institute: Element): SFaculty {
        val name = institute.attr("id")
        val rows = institute.select(".text-form__item")
        val years = mutableListOf<SYear>()
        for (row in rows) {
            val year = parseRow(row)
            years.add(year)
        }
        return SFaculty(name, years)
    }

    /**
     * Parses a row containing all groups in one year
     * @param row
     * @return Year
     */
    private fun parseRow(row: Element): SYear {
        val courseNumber = row.selectFirst(".text-form__item-name")
            ?.text()
            ?.replace("курс", "")
            ?.trim()
            ?.toByte() ?: throw ParserException("Couldn't parse course number")
        val timetableUrls = row.select(".timetable-url")
        val SGroups = mutableListOf<SGroup>()
        for (timetableUrl in timetableUrls) {
            val group = parseTimetableUrl(timetableUrl)
            SGroups.addAll(group)
        }
        return SYear(courseNumber, SGroups)
    }

    /**
     * Parses row of groups
     * @param timetableUrl Span containing a row of groups
     * @return List of groups
     */
    private fun parseTimetableUrl(timetableUrl: Element): List<SGroup> {
        val nestedLink = timetableUrl.selectFirst(".dropdown")

        if (nestedLink != null) {
            val dropdownLinks = nestedLink.select(".dropdown-item")
            val groups = mutableListOf<SGroup>()
            for (dropDownLink in dropdownLinks) {
                val url = dropDownLink.attr("href")
                val groupName = dropDownLink.text().trim()
                groups.add(SGroup(groupName, url))
            }
            return groups
        }
        else {
            val link = timetableUrl.selectFirst("a") ?: throw ParserException("Couldn't parse course number")
            val url = link.attr("href")
            val groupName = link.text().trim()
            return listOf(SGroup(groupName, url))
        }
    }
}