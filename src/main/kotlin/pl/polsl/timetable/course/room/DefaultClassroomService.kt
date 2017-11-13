package pl.polsl.timetable.course.room

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultClassroomService(@Autowired private val classroomRepository: ClassroomRepository): ClassroomService {
    override fun findOrCreate(classroom: Classroom): JpaClassroom {
        return classroomRepository
                .findByRoom(classroom.room)
                .orElse(create(classroom))
    }

    private fun create(classroom: Classroom): JpaClassroom {
        return classroomRepository.save(JpaClassroom(classroom.room))
    }
}