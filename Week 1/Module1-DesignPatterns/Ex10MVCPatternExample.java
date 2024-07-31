// Model
class Student {
    private final int id;
    private String name;
    private String grade;

    public Student(String name, int id, String grade) {
        this.name = name;
        this.id = id;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getGrade() {
        return grade;
    }
}

// View
class StudentView {
    public void displayStudentDetails(Student student) {
        System.out.println();
        System.out.println("Student Id: " + student.getId());
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student grade: " + student.getGrade());
        System.out.println();
    }
}

// Controller
class StudentController {
    private Student model;
    private StudentView view;
    
    public StudentController(Student model, StudentView view) {
        this.model = model;
        this.view = view;
    }

    public void setGrade(String grade) {
        model = new Student(model.getName(), model.getId(), grade);
    }

    public void setName(String name){
        model = new Student(name, model.getId(), model.getGrade());
    }

    public void updateView(){
        view.displayStudentDetails(model);
    }


}

public class Ex10MVCPatternExample {
    public static void main(String[] args) {
        Student student = new Student("Rahul", 1, "A");
        StudentView webApp = new StudentView();

        StudentController controller = new StudentController(student, webApp);
        controller.updateView();

        controller.setGrade("A+");
        controller.updateView();

        controller.setName("Joel");
        controller.updateView();

    }
}
