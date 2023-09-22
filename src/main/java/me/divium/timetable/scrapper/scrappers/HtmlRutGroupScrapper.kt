package me.divium.timetable.scrapper.scrappers

import me.divium.timetable.scrapper.ParserException
import me.divium.timetable.scrapper.Scrapper
import me.divium.timetable.scrapper.model.group.Group
import me.divium.timetable.scrapper.model.group.Department
import me.divium.timetable.scrapper.model.group.Year
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 * Class for parsing groups and their links
 */
class HtmlRutGroupScrapper(private var url: String) : Scrapper<List<Department>> {
    private var groups: List<Department> = listOf()

    override fun scrape() {
        groups = parseAllInstitutes(url)
    }

    override fun getResult(): List<Department> {
        return groups
    }

    /**
     * Starts parsing. Parser all institutes
     * @param url Url to parse
     * @return List of institutes
     */
    private fun parseAllInstitutes(url: String): List<Department> {
        val document = Jsoup.connect(url).get()
        val catalog = document.select("section .timetable-catalog") ?: throw ParserException("Time table catalogue not found")

        val divs = catalog.select(".info-block_collapse")
        val departments = mutableListOf<Department>()
        for (div in divs) {
            val institute = parseInstitute(div)
            departments.add(institute)
        }

        return departments
    }

    /**
     * Parses all groups in institute
     * @param institute Div containing institute
     * @return List of groups
     */
    private fun parseInstitute(institute: Element): Department {
        val name = institute.attr("id")
        val rows = institute.select(".text-form__item")
        val years = mutableListOf<Year>()
        for (row in rows) {
            val year = parseRow(row)
            years.add(year)
        }
        return Department(name, years)
    }

    /**
     * Parses a row containing all groups in one year
     * @param row
     * @return Year
     */
    private fun parseRow(row: Element): Year {
        val courseNumber = row.selectFirst(".text-form__item-name")
            ?.text()
            ?.replace("курс", "")
            ?.trim()
            ?.toByte() ?: throw ParserException("Couldn't parse course number")
        val timetableUrls = row.select(".timetable-url")
        val groups = mutableListOf<Group>()
        for (timetableUrl in timetableUrls) {
            val group = parseTimetableUrl(timetableUrl)
            groups.addAll(group)
        }
        return Year(courseNumber, groups)
    }

    /**
     * Parses row of groups
     * @param timetableUrl Span containing a row of groups
     * @return List of groups
     */
    private fun parseTimetableUrl(timetableUrl: Element): List<Group> {
        val nestedLink = timetableUrl.selectFirst(".dropdown")

        if (nestedLink != null) {
            val dropdownLinks = nestedLink.select(".dropdown-item")
            val groups = mutableListOf<Group>()
            for (dropDownLink in dropdownLinks) {
                val url = dropDownLink.attr("href")
                val groupName = dropDownLink.text().trim()
                groups.add(Group(groupName, url))
            }
            return groups
        }
        else {
            val link = timetableUrl.selectFirst("a") ?: throw ParserException("Couldn't parse course number")
            val url = link.attr("href")
            val groupName = link.text().trim()
            return listOf(Group(groupName, url))
        }
    }
}