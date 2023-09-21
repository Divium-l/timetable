import requests
from bs4 import BeautifulSoup

from timetable import Lesson

def parse_day_of_week(text: str):
    day_of_week = text.split("\n")[1]
    day_of_week = day_of_week.strip()
    return day_of_week

def parse_time(text: str):
    splited_text = text.split(",")
    time = splited_text[0].strip() # ADD regex replacing everything except number
    number = splited_text[1].strip()
    return time, number

url = "https://miit.ru/timetable/193717"
#response = requests.get(url) #UNCOMENT LATER

# Using local web page
html_file = open(file="miit-timetable.html", mode="r", encoding="utf-8").read()

soup = BeautifulSoup(html_file, 'html.parser')

# Select week container
week_1 = soup.find("div", {"id": "week-1"})
week_2 = soup.find("div", {"id": "week-2"})

weeks = [week_1, week_2]

for week in weeks:
    days = week.find_all("div", {"class": "info-block"})
    for day in days:
        header = day.find("span", {"class": "info-block__header-text"})
        day_of_week = parse_day_of_week(text=header.text)

        timeslots = day.find_all("div", {"class": "timetable__list-timeslot"})
        for timeslot in timeslots:
            lesson_time = timeslot.find("div", {"class": "mb-1"}).text
            lesson_name = timeslot.find("div", {"class": "pl-4"}).text
            time, number = parse_time(lesson_time)
            print(time)

