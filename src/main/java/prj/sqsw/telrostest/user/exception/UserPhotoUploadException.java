package prj.sqsw.telrostest.user.exception;

public class UserPhotoUploadException extends RuntimeException {
    public UserPhotoUploadException() {
        super("Error occurred when uploading profile photo");
    }
}
