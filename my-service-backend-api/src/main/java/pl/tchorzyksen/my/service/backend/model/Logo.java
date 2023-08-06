package pl.tchorzyksen.my.service.backend.model;

public record Logo(String imageName, Long contentLength, byte[] content, String contentType) {

}
