from typing import List

class LessonTime:
    number: int
    time_start: str
    time_end: str

class Lesson:
    time: LessonTime
    type_str: str
    room: str
    groups: List[str]
    teacher: str
    name: str

    def __init__(self, number:int, time: str, name: str):
        self.number = number
        self.time = time
        self.name = name

class Day:
    day: str
    lessons: List[Lesson]

    def __init__(self, day: str, lessons: List[Lesson]):
        self.day = day
        self.lessons = lessons

class TimeTable:
    days = []