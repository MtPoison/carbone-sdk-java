package io.carbone;

import static java.util.Collections.emptyMap;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Request;
import feign.Response;
public class CarboneDecoderTest {

    private Request request;
    private CarboneDecoder carboneDecoder;

    @Before
    public void setUp() {
        request = Request.create(Request.HttpMethod.GET, "url", emptyMap(), Request.Body.empty(), null);

        carboneDecoder = new CarboneDecoder();
    }

    @Test
    public void doitDecoderUneCarboneResponse() throws IOException {
        //GIVEN
        CarboneResponse carboneResponse = CarboneResponse.builder()
            .success(true)
            .build();

        Response feignResponse = Response.builder()
            .request(request)
            .body(new ObjectMapper().writeValueAsBytes(carboneResponse))
            .build();


        // WHEN
        Object decodedObj = carboneDecoder.decode(feignResponse, CarboneResponse.class);

        // THEN
        Assertions.assertThat(decodedObj)
            .isInstanceOf(CarboneResponse.class);
    }

    @Test
    public void doitDecoderUneCarboneFileResponse() throws IOException {
        //GIVEN
        CarboneFileResponse carboneResponse = new CarboneFileResponse(new byte[]{});

        Response feignResponse = Response.builder()
            .request(request)
            .body(new ObjectMapper().writeValueAsBytes(carboneResponse))
            .build();


        // WHEN
        Object decodedObj = carboneDecoder.decode(feignResponse, CarboneFileResponse.class);

        // THEN
        Assertions.assertThat(decodedObj)
            .isInstanceOf(CarboneFileResponse.class);
    }

    @Test
    public void doitDecoderUneCarboneDocument() throws IOException {
        //GIVEN
        CarboneDocument carboneResponse = new CarboneDocument(new byte[]{}, new String());

        Response feignResponse = Response.builder()
            .request(request)
            .body(new ObjectMapper().writeValueAsBytes(carboneResponse))
            .build();


        // WHEN
        Object decodedObj = carboneDecoder.decode(feignResponse, CarboneFileResponse.class);

        // THEN
        Assertions.assertThat(decodedObj)
            .isInstanceOf(CarboneFileResponse.class);
    }
}