package prj.sqsw.telrostest.user.exception;

public class UserPhotoDownloadException extends RuntimeException {
    public UserPhotoDownloadException() {
        super("Error occurred when downloading profile photo");
    }
}

