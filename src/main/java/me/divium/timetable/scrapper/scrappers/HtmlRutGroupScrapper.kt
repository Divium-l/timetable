package me.divium.timetable.scrapper.scrappers

import me.divium.timetable.scrapper.ParserException
import me.divium.timetable.scrapper.Scrapper
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class HtmlRutGroupScrapper(var url: String) : Scrapper {
    override fun scrape() {
        TODO("Not yet implemented")
    }

    fun ljdfkl(url: String) {
        val document = Jsoup.connect(url).get()
        val catalog = document.select("section .timetable-catalog") ?: throw ParserException("Time table catalogue not found")

        val divs = catalog.select("div")
        for (i in 1 until divs.size) {

        }
    }

    fun parseInstitute(institute: Element) {
        val name = institute.attr("id")
        val rows = institute.select(".text-form__item")
        for (row in rows) {
            parseRow(row)
        }
    }

    fun parseRow(row: Element) {
        val courseNumber = row.selectFirst("text-form__item-name")
            ?.text()
            ?.replace("курс", "")
            ?.trim()
            ?.toByte() ?: throw ParserException("Couldn't parse course number")
        val timetableUrls = row.select(".timetable-url")
        for (timetableUrl in timetableUrls) {
            parseTimetableUrl(timetableUrl)
        }
    }

    fun parseTimetableUrl(timetableUrl: Element) {
        val nestedLink = timetableUrl.selectFirst(".dropdown")

        if (nestedLink != null) {
            val dropdownLinks = nestedLink.select(".dropdown-item")
            for (dropDownLink in dropdownLinks) {
                val url = dropDownLink.attr("href")
                val groupName = dropDownLink.text().trim()
            }
        }

        val link = timetableUrl.selectFirst("a") ?: throw ParserException("Couldn't parse course number")
        val url = link.attr("href")
        val groupName = link.text().trim()
    }
}