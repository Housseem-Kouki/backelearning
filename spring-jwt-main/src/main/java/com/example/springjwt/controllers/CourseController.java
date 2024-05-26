package com.example.springjwt.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.example.springjwt.dto.*;
import com.example.springjwt.model.*;
import com.example.springjwt.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.springjwt.services.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/rest/course")
@AllArgsConstructor
public class CourseController {

    private LessonRepository lessonRepository;
    private ChapterRepository chapterRepository;
    private final ObjectMapper objectMapper;
    private StepcourseRepository stepcourseRepository;
	@Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/courses")
    public List<Course> getCourses() {
        
        return courseService.getCourses();
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable int courseId) {
        try {
            Course foundCourse = courseService.getCourseById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException("Requested course not found"));
            return ResponseEntity.ok().body(foundCourse);
        } catch (EntityNotFoundException ex) {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "Course not found for id: " + courseId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
   // , consumes ={"application/octet-stream","multipart/form-data"}

   @PostMapping(path = "/courses")
   public ResponseEntity<?> addCourse(@RequestParam("course") String course, @RequestParam("file") MultipartFile file) {
	   try {
	  
	   ObjectMapper objectMapper = new ObjectMapper();

       CourseDTO courseDTO = objectMapper.readValue(course, CourseDTO.class);
	   Course c=CourseConverter.toEntity(courseDTO);
	 //  c.setCoursePhotoFile(String.valueOf(file.getBytes()));
    	return ResponseEntity.ok(courseService.save(c));
       
    } catch (Exception e) {
    	 e.printStackTrace();
    	return null;
       
    }
    
  }

@PutMapping("/courses/{courseId}")
public ResponseEntity<?> updateCourse(@RequestBody Course course, @PathVariable int courseId) {
    if (courseService.existsById(courseId)) {
        Course existingCourse = courseService.getCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Requested course not found"));
        // Update the existing course fields
        existingCourse.setCourseTitle(course.getCourseTitle());
        existingCourse.setCourseCategory(course.getCourseCategory());
        existingCourse.setCourseDescription(course.getCourseDescription());
        existingCourse.setCourseDuration(course.getCourseDuration());
        existingCourse.setCourseLevel(course.getCourseLevel());
        existingCourse.setCourseIsPremium(course.getCourseIsPremium());
        existingCourse.setCoursePhotoFile(course.getCoursePhotoFile());
        existingCourse.setLessons(course.getLessons());
        courseService.save(existingCourse);
        return ResponseEntity.ok().body(existingCourse);
    } else {
        HashMap<String, String> message = new HashMap<>();
        message.put("message", courseId + " course not found or matched");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}


    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courseId) {
        if (courseService.existsById(courseId)) {
        	courseService.deleteCourse(courseId);
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "Course with id " + courseId + " was deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", courseId + " course not found or matched");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
    
    
    @PostMapping(path = "/course")
    public ResponseEntity<?> addCourses(@RequestBody CourseDTO coursDto) {
 	   try {	   	  
 		   Course course = CourseConverter.toEntity(coursDto);  		   
 		   Course savedCourse = courseService.save(course); 
 		   CourseDTO courseSaved = CourseConverter.toDTO(savedCourse);
		   Course courseSavede = CourseConverter.toEntity(courseSaved);
		   Course savedCourses = courseService.save(courseSavede); 
 		  return ResponseEntity.ok().body(savedCourses);
     } catch (Exception e) {
     	 e.printStackTrace();
     	return null;
        
     }
    }


    @PostMapping(path = "/addCoursesFinal/{idUser}")
    public Course addCoursesFinal(@RequestParam("course") String courseJson,
                                             @RequestParam("file") MultipartFile file,
                                  @PathVariable("idUser")int idUser) {
        try {
            Course course = objectMapper.readValue(courseJson, Course.class);
            course.setCoursePhotoFile(file.getBytes());
            course.setCoursePubDate(LocalDateTime.now());
            // Récupérer le nom original du fichier
            String fileName = file.getOriginalFilename();
            // Sauvegarder le fichier dans le dossier d'uploads avec un nom spécifique
            byte[] fileBytes = file.getBytes();
            Path filePath = Paths.get("src/main/resources/static/uploads/" + fileName);
            Files.write(filePath, fileBytes);
// Enregistrer le nom du fichier dans l'objet Course
            course.setFileName(fileName);

            //current user
            User currentUser = userRepository.findById(idUser).orElse(null);
            course.setTeacher(currentUser);
            // Sauvegarde du cours
            Course savedCourse = courseRepository.save(course);

            System.out.println("date "+savedCourse.getCoursePubDate());
            // Vous n'avez probablement pas besoin de modifier la durée ici
            // course.setCourseDuration(course.getCourseDuration());

            // Retourner le cours sauvegardé
           // return ResponseEntity.ok().body(savedCourse);
            return savedCourse;
        } catch (Exception e) {
            e.printStackTrace();
            // En cas d'erreur, renvoyer une réponse d'erreur appropriée
           // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du cours");
            return null;
        }
    }


    @PostMapping(path = "/addContentToCourse/{courseId}")
    @Transactional
    public Course addContentToCourse(@PathVariable("courseId") int courseId, @RequestBody CourseDTO courseDTO) {
        System.out.println("course : "+courseDTO);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Handle Lessons
        List<Lesson> lessons = new ArrayList<>();
        for (LessonDTO lessonDTO : courseDTO.getLessons()) {
            Lesson lesson = new Lesson();
            lesson.setLessonTitle(lessonDTO.getLessonTitle());
            lesson.setLessonDescription(lessonDTO.getLessonDescription());
            lesson.setCourse(course);

            // Handle Quizzes
            List<Quiz> quizzes = new ArrayList<>();
            for (QuizDTO quizDTO : lessonDTO.getLessonQuizzes()) {
                Quiz quiz = new Quiz();
                quiz.setQuizQuestion(quizDTO.getQuizQuestion());
                quiz.setQuizDescription(quizDTO.getQuizDescription());
                quiz.setLesson(lesson);

                List<Answer> answers = new ArrayList<>();
                for (AnswerDTO answerDTO : quizDTO.getAnswers()) {
                    Answer answer = new Answer();
                    answer.setAnswer(answerDTO.getText());
                    answer.setCorrect(answerDTO.isCorrect());
                    answer.setQuiz(quiz);
                    answers.add(answer);
                }
                quiz.setAnswers(answers);
                quizzes.add(quiz);
            }
            lesson.setQuizzes(quizzes);

            // Handle Chapters
            List<Chapter> chapters = new ArrayList<>();
            for (ChapterDTO chapterDTO : lessonDTO.getLessonChapters()) {
                Chapter chapter = new Chapter();
                chapter.setChapterTitle(chapterDTO.getChapterTitle());
                chapter.setChapterDescription(chapterDTO.getChapterDescription());
                chapter.setChapterVideo(chapterDTO.getChapterVideo());
                chapter.setLesson(lesson); // Associate chapter with lesson
                chapters.add(chapter);
            }
            lesson.setChapters(chapters); // Add chapters to lesson

            lessons.add(lesson);
        }
        lessonRepository.saveAll(lessons);
        course.setLessons(lessons);

        return courseRepository.save(course);
    }


    @GetMapping("/getAllCourses")
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }


    @GetMapping("/getAllCoursesByTeacher/{idtecher}")
    public List<Course> getAllCoursesByTeacher(@PathVariable("idtecher") int idtecher){
        User user = userRepository.findById(idtecher).orElse(null);
        return courseRepository.findCourseByTeacher(user);

    }

    @GetMapping("/getCourseById/{idCourse}")
    public Course getCourseById2(@PathVariable("idCourse") int idCourse){
       // User user = userRepository.findById(idCourse).orElse(null);
        return courseRepository.findById(idCourse).orElse(null);

    }


    @GetMapping("/getCoursesForStudent/{studentId}")
    public List<Course> getCoursesForStudent(@PathVariable int studentId) {
        User student = userRepository.findById(studentId).orElse(null);
       // List<Course> courses = courseRepository.
        List<Course> courses = new ArrayList<>();
        List<Stepcourse> stepcourses = stepcourseRepository.findByStudent(student);
        for (Stepcourse stepcourse : stepcourses) {
            courses.add(stepcourse.getCourse());
        }
        return courses;
    }





    @PostMapping("/assignCourseToStudent2/{studentId}/{courseId}")
    public void assignCourseToStudent2(@PathVariable int studentId, @PathVariable int courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        // Check if the Stepcourse already exists
        boolean alreadyAssigned = stepcourseRepository.existsByStudentAndCourse(student, course);
        if (alreadyAssigned) {
            throw new RuntimeException("Student is already assigned to this course");
        }
        // Create new Stepcourse entity
        Stepcourse stepcourse = new Stepcourse();
        stepcourse.setStudent(student);
        stepcourse.setCourse(course);
        stepcourse.setEtat(1); // Or any default value you want to set

        // Save the new Stepcourse entity
        stepcourseRepository.save(stepcourse);
    }

    @GetMapping("/getEtatCourseByStudent/{studentId}/{courseId}")
    public int getEtatCourseByStudent(@PathVariable int studentId, @PathVariable int courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
            Stepcourse stepcourse = stepcourseRepository.findByStudentAndCourse(student,course);
            return stepcourse.getEtat();
    }

    @PutMapping("/courses/{courseId}/hide")
    public ResponseEntity<?> hideCourse(@PathVariable int courseId) {
        if (courseService.existsById(courseId)) {
            Course existingCourse = courseService.getCourseById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException("Requested course not found"));
            existingCourse.setStatus(false); // Set status to false to hide the course
            courseService.save(existingCourse);
            return ResponseEntity.ok().body("Course with ID " + courseId + " hidden successfully");
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", courseId + " course not found or matched");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PutMapping("/courses/{courseId}/reveal")
    public ResponseEntity<?> revealCourse(@PathVariable int courseId) {
        if (courseService.existsById(courseId)) {
            Course existingCourse = courseService.getCourseById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException("Requested course not found"));
            existingCourse.setStatus(true); // Set status to true to reveal the course
            courseService.save(existingCourse);
            return ResponseEntity.ok().body("Course with ID " + courseId + " revealed successfully");
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", courseId + " course not found or matched");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }


    @DeleteMapping("/deleteCourse2/{idCourse}")
    public void deleteCourse2(@PathVariable("idCourse")int idCourse){
        Course course = courseRepository.findById(idCourse).orElse(null);
        courseRepository.delete(course);
    }
}
