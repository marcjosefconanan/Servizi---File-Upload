package Servizi.File.Upload.Servizi.File.Upload.Dto;

import Servizi.File.Upload.Servizi.File.Upload.Entities.User;

public class DownloadProfilePictureDto {
    private User user;
    private byte[] profileImage;

    public DownloadProfilePictureDto(){
    }

    public DownloadProfilePictureDto(User user, byte[] profileImage) {
        this.user = user;
        this.profileImage = profileImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}