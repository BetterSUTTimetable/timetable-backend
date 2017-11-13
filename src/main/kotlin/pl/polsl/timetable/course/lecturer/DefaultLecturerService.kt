package pl.polsl.timetable.course.lecturer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultLecturerService(@Autowired private val lecturerRepository: LecturerRepository): LecturerService {
    override fun findOrCreate(lecturer: Lecturer): JpaLecturer {
        return lecturerRepository
                .findByFullNameAndShortName(fullName = lecturer.fullName, shortName = lecturer.shortName)
                .orElse(create(lecturer))
    }

    private fun create(lecturer: Lecturer): JpaLecturer {
        return lecturerRepository.save(JpaLecturer(fullName = lecturer.fullName, shortName = lecturer.shortName))
    }
}