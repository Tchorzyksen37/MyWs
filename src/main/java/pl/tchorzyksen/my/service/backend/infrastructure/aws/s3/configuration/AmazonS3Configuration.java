package pl.tchorzyksen.my.service.backend.infrastructure.aws.s3.configuration;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;

@Configuration
class AmazonS3Configuration {

  @Value("${app.object-storage.url}")
  private String s3Url;

  @Bean
  AmazonS3 amazonS3() {
    var credentialsProviderChain = new AWSCredentialsProviderChain(new EnvironmentVariableCredentialsProvider());
    return AmazonS3ClientBuilder.standard()
            .withCredentials(credentialsProviderChain)
            .withEndpointConfiguration(new EndpointConfiguration(s3Url, "eu-central-1"))
            .build();
  }

}
