package app.error.exception;

public class ProjectNotFoundException extends NotFoundException {
    public ProjectNotFoundException(Long id){
        super("project", id);
    }
}
