package com.mattel.ecomm.fedex.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.FedExResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class FedExServiceImplTest {
	private final String endPointUrl = "http://localhost:PORT_NUMBER/QA/store/STORE_ID/fedex/generateReturnLabel?responseFormat=json&updateCookies=true&api_key=muzt944eh78z8aqmqc9xkqfv";
	private MockWebServer mockWebServer;
	@Mock
	private PropertyReaderService propertyReaderService;
	@InjectMocks
	private FedExServiceImpl impl;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFetchForSuccess() throws ServiceException, IOException {
		try (InputStream is2 = getClass().getResourceAsStream("fedex_response.json");
				InputStream is1 = getClass().getResourceAsStream("fedex_request.json")) {
			String imageVal = "JVBERi0xLjQKMSAwIG9iago8PAovVHlwZSAvQ2F0YWxvZwovUGFnZXMgMyAwIFIKPj4KZW5kb2JqCjIgMCBvYmoKPDwKL1R5cGUgL091dGxpbmVzCi9Db3VudCAwCj4+CmVuZG9iagozIDAgb2JqCjw8Ci9UeXBlIC9QYWdlcwovQ291bnQgMQovS2lkcyBbMTggMCBSXQo+PgplbmRvYmoKNCAwIG9iagpbL1BERiAvVGV4dCAvSW1hZ2VCIC9JbWFnZUMgL0ltYWdlSV0KZW5kb2JqCjUgMCBvYmoKPDwKL1R5cGUgL0ZvbnQKL1N1YnR5cGUgL1R5cGUxCi9CYXNlRm9udCAvSGVsdmV0aWNhCi9FbmNvZGluZyAvTWFjUm9tYW5FbmNvZGluZwo+PgplbmRvYmoKNiAwIG9iago8PAovVHlwZSAvRm9udAovU3VidHlwZSAvVHlwZTEKL0Jhc2VGb250IC9IZWx2ZXRpY2EtQm9sZAovRW5jb2RpbmcgL01hY1JvbWFuRW5jb2RpbmcKPj4KZW5kb2JqCjcgMCBvYmoKPDwKL1R5cGUgL0ZvbnQKL1N1YnR5cGUgL1R5cGUxCi9CYXNlRm9udCAvSGVsdmV0aWNhLU9ibGlxdWUKL0VuY29kaW5nIC9NYWNSb21hbkVuY29kaW5nCj4+CmVuZG9iago4IDAgb2JqCjw8Ci9UeXBlIC9Gb250Ci9TdWJ0eXBlIC9UeXBlMQovQmFzZUZvbnQgL0hlbHZldGljYS1Cb2xkT2JsaXF1ZQovRW5jb2RpbmcgL01hY1JvbWFuRW5jb2RpbmcKPj4KZW5kb2JqCjkgMCBvYmoKPDwKL1R5cGUgL0ZvbnQKL1N1YnR5cGUgL1R5cGUxCi9CYXNlRm9udCAvQ291cmllcgovRW5jb2RpbmcgL01hY1JvbWFuRW5jb2RpbmcKPj4KZW5kb2JqCjEwIDAgb2JqCjw8Ci9UeXBlIC9Gb250Ci9TdWJ0eXBlIC9UeXBlMQovQmFzZUZvbnQgL0NvdXJpZXItQm9sZAovRW5jb2RpbmcgL01hY1JvbWFuRW5jb2RpbmcKPj4KZW5kb2JqCjExIDAgb2JqCjw8Ci9UeXBlIC9Gb250Ci9TdWJ0eXBlIC9UeXBlMQovQmFzZUZvbnQgL0NvdXJpZXItT2JsaXF1ZQovRW5jb2RpbmcgL01hY1JvbWFuRW5jb2RpbmcKPj4KZW5kb2JqCjEyIDAgb2JqCjw8Ci9UeXBlIC9Gb250Ci9TdWJ0eXBlIC9UeXBlMQovQmFzZUZvbnQgL0NvdXJpZXItQm9sZE9ibGlxdWUKL0VuY29kaW5nIC9NYWNSb21hbkVuY29kaW5nCj4+CmVuZG9iagoxMyAwIG9iago8PAovVHlwZSAvRm9udAovU3VidHlwZSAvVHlwZTEKL0Jhc2VGb250IC9UaW1lcy1Sb21hbgovRW5jb2RpbmcgL01hY1JvbWFuRW5jb2RpbmcKPj4KZW5kb2JqCjE0IDAgb2JqCjw8Ci9UeXBlIC9Gb250Ci9TdWJ0eXBlIC9UeXBlMQovQmFzZUZvbnQgL1RpbWVzLUJvbGQKL0VuY29kaW5nIC9NYWNSb21hbkVuY29kaW5nCj4+CmVuZG9iagoxNSAwIG9iago8PAovVHlwZSAvRm9udAovU3VidHlwZSAvVHlwZTEKL0Jhc2VGb250IC9UaW1lcy1JdGFsaWMKL0VuY29kaW5nIC9NYWNSb21hbkVuY29kaW5nCj4+CmVuZG9iagoxNiAwIG9iago8PAovVHlwZSAvRm9udAovU3VidHlwZSAvVHlwZTEKL0Jhc2VGb250IC9UaW1lcy1Cb2xkSXRhbGljCi9FbmNvZGluZyAvTWFjUm9tYW5FbmNvZGluZwo+PgplbmRvYmoKMTcgMCBvYmogCjw8Ci9DcmVhdGlvbkRhdGUgKEQ6MjAwMykKL1Byb2R1Y2VyIChGZWRFeCBTZXJ2aWNlcykKL1RpdGxlIChGZWRFeCBTaGlwcGluZyBMYWJlbCkNL0NyZWF0b3IgKEZlZEV4IEN1c3RvbWVyIEF1dG9tYXRpb24pDS9BdXRob3IgKENMUyBWZXJzaW9uIDUxMjAxMzUpCj4+CmVuZG9iagoxOCAwIG9iago8PAovVHlwZSAvUGFnZQ0vUGFyZW50IDMgMCBSCi9SZXNvdXJjZXMgPDwgL1Byb2NTZXQgNCAwIFIgCiAvRm9udCA8PCAvRjEgNSAwIFIgCi9GMiA2IDAgUiAKL0YzIDcgMCBSIAovRjQgOCAwIFIgCi9GNSA5IDAgUiAKL0Y2IDEwIDAgUiAKL0Y3IDExIDAgUiAKL0Y4IDEyIDAgUiAKL0Y5IDEzIDAgUiAKL0YxMCAxNCAwIFIgCi9GMTEgMTUgMCBSIAovRjEyIDE2IDAgUiAKID4+Ci9YT2JqZWN0IDw8IC9UaHJlZUhCYXJzIDIwIDAgUgovYmFyY29kZTAgMjEgMCBSCj4+Cj4+Ci9NZWRpYUJveCBbMCAwIDYxMiA3OTJdCi9UcmltQm94WzAgMCA2MTIgNzkyXQovQ29udGVudHMgMTkgMCBSCi9Sb3RhdGUgMD4+CmVuZG9iagoxOSAwIG9iago8PCAvTGVuZ3RoIDMzOTAKL0ZpbHRlciBbL0FTQ0lJODVEZWNvZGUgL0ZsYXRlRGVjb2RlXSAKPj4Kc3RyZWFtCkdhdFU3OW9nNygmXFNTQUopRSU5ZW5sMjtsISc0dTFTWzJbLjhuSzxJR0FZLyJAOCFjM2VtXzJvbSVnMEE8NGgmJU5RJmdPJ0FnUjw+Q0cyCi5bJUIyPSlXI0IrPVpIZlhoNUQ/SW9oNVRgIiovayNPYHI7LWwjKEFUVWtUJyUiTmUxTSpJTi1xPTFnLmooPEEkY0tARjtzJy86aWw2Xz80CiZSUXMyVyRjOkY5Lj1jIW1IT1RFNW9iKystVml0VEVTcE4pcUVObDhiUE1oKWMiOG9aTF0lYkNyRG0kO1NQbiY5a0AtZU9YRUhzbzhyQEg3CmBgaW0qcSdXSDVFVXNaTSdlSDtYSS9FcEBkb01ac01jY0A9MjNNTFdacFtxc1JpSk1dKSwsP00+Kjpfamk8KTFYVDBLSnIpRDt1aUorIlw5ClwiXmBwOjBPVz5KLlgoXzQ6TmJjVlphKzEuQmVGJmsiQEpmaXJoKylLIVdsOC5QXGlFYiQtWChORlYndTk4VlAsX1NcSlZeXFguIllNQSZwCjBVZG1MMkhtKVVNRlApdW84KSV0TiFFSyJeTzA0dVNjQTZVajJfJWRUME4/W0Nqb1tnSDAqUWZuciRnKC9IYFpJQ2dwRD9aOVQ6ZDhZPmpfCkZSX0VITTVURFNCbWYnYTA2WkpTMCFJL0YhVlIoViwiTXRyRC1UR14lL1o9dDktY0VuXkcyVT1VLCxnb2ArLDc/UU8pSWpgOjdmPGExTFNdCjpjImhWRU5AUjpBI11kWGVPcSs+Ok1WNTtuYW8rYERkT2NqPF9NLGhgI0gxdEJkOic1MSNJSllCJm9QOjRtb2Q4S1liZlZIQyFOTzBPJE9cCidbOD0kYGQ2ZV8kKV1Fb21QcGZoZG5WSC5vL3JSbWdvYiJZQDctdWYtX11eWW5NSS5gR18kdEVZIlghISwicj1XVDFNKD1WaGFYanA0aVleCiI7QydbXV0+PipQPitbdUpoLCknV0R1PyYzPzg4Xy5lVWE3YFVJWSVeLzFbOXA7TUxJT1FuTF0+REVONyx0MFxlNnBKMk8+P20hPE9uRDk1Cj9mbzo9L2ApPChvQzRCR1Y1NSovVXRmPmlFQHFBRXJwc2xpO0teUGQpQVVBL0B0W1JvUDZRdUFbWS1NK0h0cVomUV8qNj1wdClSc3E6MUEqClRkaGpkXzw4aydFaCgtXzFKQlpkQU83ayZBX2ReTTM7RC09VkRzWWc1Ikc2UlxoWllnTjkpZEhePDJrOkU8cVRLRFFPRigpTVksREZaUmclCitfNWJ0MU8rTzNTZE8nNTJJcGlyLDZWIjgwRy42dGFdJCk8bUk+QUhxLlhwZCRfQ2hlVFNCXjZtRWtSI1ViSXJRYD1hWXMyVSsrWmRxYU0sClw8OUotVEBdJVJDMDdDY0pmY0JLMFxTVSRVaDRqJD09Nm4/Xy5HM2dOKjlCJWZNZltLN2ptImcvcy9xXilpPFVTQChsOVAnITtwW29OREY7Cm41UVZlSFZjKVRkVGttM0xxJTBRXmlgZ0tXKVJWazZxKDRGUG1LIT9MJFEuJzptXjxlI0QmW0lNSzJCSlkxRyJiNTxBK0ZVWExXSlIuLCJPCjNwLyJqKixPMUBvYydvNy9oXSYjSUVNYEwpLCsnZztTYDFiOVBXTDNTWXM+dE9vZkBZTjpBSUgyNj5EUFdELHJtVSsoREdSW0Q9VF8iQ2xhCjNvLV4zM2VgY1hsdCEsOWgrVlxsPV1bcz1jWFFBYDtoYU5HI0YsLT0zPkdnW2duSEFqPDkmOjsvTlMqdWosPm44Mzl1cnBZKD5uTVo7YS4sCk5AYktDY0QjKVIycDhNa0BFOWw+IWJZNzNsZk8mQTxJY2JrJ3M5TFoqMG1gYzNlaTBuTF5XWWlmP0p1VzE2IiQiMywmRyFfX0cwJD83KkkvCkFpQU1BVlU5RyU8OSY7Uk9Pa2Q3UnQrQnEmaik6bD5dMyNUWDkkRW5RREJGUWFsL1U6PlZCI1AtJFpRL11fPltsPykpXi9MTCopRSJCTDAyClpdOyhvXWZdL1hBODBIXz1MPCRQSStQbDdeSTspLC0sUzVbLE1BSmFqMTpZcSZAOE9VOCFAWztOaUJtPDErQjNGJU5SX0BBQFJOKUwzOmRECkwmKktYTiFJJW1hUk5OUVxwN2JhM3VpaFRlVEdEO10qPkZpRihFSThMQyQ6TkMhTGlZWSskbCloK2QmYCJqX0BZJTomKCQ2R0dMWVg9SiVvCihkNCdgQ0tVJFIuOUFQa0xTRFsqRnNFcjpsWGwkLDpSNjtuX1tyUE5kU0wldDQlXm4zbzQrdHVGbmdvUkxEbmNbUFAvUnMhWGJ0PipTNks0CkU0dGRMPDJnMjosQiNbYEdTUjxgPlpFMF5EPVdNb1BSXGlWO3NRQV1dcmpfSlFqV3RWWyMpUSE3NV81LCo3QTdtT3FAK1IjL2AlISU6Jj9FCj1eTylJIVlVI205QGIkNGJNdXVFZWw4UUxgUTFKUiJtWlIka0QoUjMxLT87UzJdK1pCTUJJLUgvbGtmJ1cuSzBgZld0O2xVLDVfX0cibnVWCl1nOmgwMzw0a0xtZ2otJCxKJlgmTC1qNjU+PVJbR0wkRmNZV0YzRTI2WkpMOkpWYDVUXS1BPEEyNj5ESitkT3BGTGoiSS8rMDhtJWNkTW4mCl4kTS0rUio0R2E3Jy5hbEg9VUhsLGlQOTFJbzIkXl5XWVRZRFwqWjNUai5CYDNaakpiOCpbJGwxUHJPLyNMKEZfXHBzUE9vLlJsWG9xTyZDClxrQXJrbDtYITdgbEAocyxnLDpRTSk+QkkrWlM3NGUiNkIwS2hIVjFUJkdQW2pEdUgwXWc8RSorIVZCaFVpIyU3LlRkY2VnPlhNTjQ5PCxuCjNca1UkZT9EW2Q7dGM2RCMxW1pUMjMmaHAlbzZAMTFJJT1AS1Q+NS8uTiQoT1NlKnJSbSFqLz5gLUpKPm9tK2U3RDIhKCRIXEIja0dcVUtdCjRdbEtrPEYkU0FOVSVLKClqVz81IjtlPkdFQVNFM2NZbFJFaCkkISQ4b1ZhPDVuMy5VVGksZmsiUDBFXzxKSUVrRmE7NiJPcTxIWTUpdE1xCldGMjpaS0coRF80RVJxXTlhMGpAMjY3KjFAOj5OSlozYGxzX24oNlg8QUJbKyEtdDFFSkshLCYxWG1bcUxCN0BgJidmTVNZO0w5SWsvP1BeCjpuK0Imb2JlSWRfQD9TaSY4RWNBVEVfUFAlKVVIVVYqYlFIUCsrTmpYTlBOamZpTTYxRChbZWNbQjxyL2BsLDBaVis/ZWlNVF1jbVhIK14uClo+OnJucV1vZkpMJEZTSFpvc2BkZ1UmPDJmZDQiW0ZVXyU6LHVCbFNLSSJrUUlndGA3KGw0TFw0WD1FKkJucz1CZSYoZEZBIyhVRz48K09ZClFGTFlYX0s/Yy1RZkRCJF1ITU47YyJaLzUkc04oLkkjLztlVm9KQkk2dW4uOGtVOiI0Tmk3TnBNSkdTakQjXDMvaFQiY0heaSYqbC9UUlxCCmVdOmhWaENRXUdfKWdyaWI+QjwhQGdrJlQlMnVJUjMpSG1gWEEyS1VqPTdTQlhILGY6UyNiODlbbDchYTtrczEvVk8iWTNLb3V1MVVxKSVqCmkmWl4oZmJSKnBrKWQtYDVjXD0hNTFAXjonY2opRTJrViVTKVYsX0xsJ2NyX1I+dHQ8YWAxZmhfdG5iSHFYSnF1LXIhNWEzXyosYnFMI2E8CjpgLltwIU0naXI7clxeZERyJWxzbThBOG5YNStxc19XQyhzJiojYVZnUSlsLzdsQ2F1bUtMYWJvJDQoXjFcbzIkWFknWEQwPVVJI0Q0P0AxCmg9QWJQXkhaUz5raVJrOz0sS0U6YGhHcVFUWmNjR1hlXENIWkApZEdqVlEhVUhyISMpKE4kYk5RbyVfT29iMzUhcUtAL01IT19JN1kuZCZtCnAjWG9zRUFybT84X2JBMSltTD8jVk9cUnUpZmFZbWxSQ1pWKmxbWz1cQHJjN1A/OG5KITFwK0UjUTRHWUZCW1ZrXCtAOjg2Y3IhRWopR15wCks6KSxKM1NhdVgycmVWI2ZNJydbPEspWmknSkNwVzpAbjFgKFBnYHJbKll0cl1zc0tLQmM4cDVqImhiQ1ZwLT5qXk9ILTE0UktXVF02RTZIClE2I1pxcGkrc0REPjMoLkgyLzY8U0Y8ci0sPlxKPXBkXjsjVjU6Zy1nJSMlcSs0YzUtTE0sJlNuYVVdYFM+V15HVCNMKTJJLCRqUFZaISNiCj82OjZJb1tAbzJncytPKFMnPGkvJDhsYjFdM1RgMEk2WD1rVlhrPS1nYD8qNGhrUW0mUVonN2xPXC1bSlg0R2E7MmEyTltfcjosMkRQdGJPClA4TW91Qy1ONlRZQGxVblciUkBFYCtTYWBTTmhqWlJOXjMrP2JSS0stXTBCWi5zMVQtUzlvPlEsMF8+LEorIkBjY05+PgplbmRzdHJlYW0KZW5kb2JqCjIwIDAgb2JqCjw8IC9UeXBlIC9YT2JqZWN0Ci9TdWJ0eXBlIC9JbWFnZQovV2lkdGggMjE0Ci9IZWlnaHQgNzQKL0NvbG9yU3BhY2UgL0RldmljZVJHQgovQml0c1BlckNvbXBvbmVudCA4Ci9MZW5ndGggMTkxCi9GaWx0ZXIgWy9BU0NJSTg1RGVjb2RlIC9GbGF0ZURlY29kZV0KPj5zdHJlYW0KR2IiMFNZbW5LJiUpQitIcyllKmdbbTMyUGhPbVUrISEhIzc/NkB0IzVbIk1qVipKMmtVbnA9WyREYUxgPXBoKCU1WyJNalYqSjNPSkgsWk0Kel1XR01rWElxZ14kRGFMYD1waCglNVsiTWpWKkoya1VucD1bJERhTGA+IW1pQHohISdbNj9JbDNONVsiTWpWKkoya1VucD1bJERhTGA9cGgKKCU1WyJNalYqSjNPSkgsWk0hNVI/PHJqJyw1fj4KZW5kc3RyZWFtCmVuZG9iagoyMSAwIG9iago8PCAvVHlwZSAvWE9iamVjdAovU3VidHlwZSAvSW1hZ2UKL1dpZHRoIDI2MAovSGVpZ2h0IDczCi9Db2xvclNwYWNlIC9EZXZpY2VHcmF5Ci9CaXRzUGVyQ29tcG9uZW50IDgKL0xlbmd0aCAxMzE3Ci9GaWx0ZXIgWy9BU0NJSTg1RGVjb2RlIC9GbGF0ZURlY29kZV0KPj5zdHJlYW0KR2IiL2RnUUVJQCRxJ15mNUhSblhGbio2bzFdKSFPOnNjUlxrdEtiUnBVLjJRXWpFYFBeQWtoVWxCO183LVwmbS1UQ2hqUiQySGM2SF4zMHEKPV9SSydkL2tzO2s/IXIwXT5TZGguYzs3I21eMkEuKSheRj8wTDRcdVVkUVJGT3EqR2xcUkZzailtIUc2Y04vJFpoNEJQRmxwPkYtSzVsbEAKXGRYbkAnKl48XDo3OUdLSEhoY2lTaUsyPjhFSF5YUm84YU1aQ0AwWyUzTXNPcDQ7SXNRPXRlR1FdbCFiJG0lKSZWUlVAXDIzWFIpLFd0K2UKZE9DV20mN1MpWzYjIUk4UzUjKVgialoucUAsITlKYj91NENCQCU1TkhvaTUyLlQlPGZAI19tUykjXnJGKyhcMUFLOk0pcGA+MlRQQzVPJVEKWzI1LkYxOllOY2ctQjNqRFlhbl85SC9wXGNAMiohaEBlTnFybSZuQmYvYWNTVjUnc3BfVVlfS2I7KjAocERCaUcidSQuT2NwJEZNW0s4SFwKOUlZIT0mQS4qaTFLRjQsZCdIRWxGYHJXcVBZSG1qWl1yUkNZUktxVDMtVjBoMHJqSTpLJ0hEWT5OQy0mQ0hCREVHSjxwb2svZEg2O0MwXkgKMG9ibVg6OmdxI18oPmsoTDFGMzI+QFslRC1OJHJXISRhQl1icDRJMTpgUTF0R2BvSF9WQzJCVi1yajxvZG1fR2YpKkQ8Yls5c0ckOHUrN2QKSVJAOGg2cTUnQGk6OCVsUC5EREJaNigyJ1VTJDd0LmYxaEttNHFjSnBETSVIRmQ2T2hJQ002J25wTVFaP21NUCZbNzMlXi1gRWVDLmc0cywKYGhIUFszSChrYjEwPWBWKGJlR0BgcnNdMz1tXzBWYnQ1YGhjP3BQKmxoWE4hPjo7ckMrblh1bk4pYnFCJXRZTywpKF5ESTBIYWpyUTg0NSUKKFhbaDBaVD5Bbk4vY2RBcTgtL2E0US9cKkJCPiZiQlcsRWxYVk9MPyUkbl5dInBtVWpVMlJQcDBvJlM9NydULCgxIWNhNTxORUtvM0ErVEgKWUMoYG9tKTlTIV0hMlswXytiLitAK0BwcCc3SytiMTAiaVxdJjdQM0Bqci0iMyJFOzQ6cGhiKFoqPjJWQXFkUlVrVj82RSVpQ2haYlVAaDIKSEZBLGMsXUpgVnIla1QnLU83XTxnXUlrV1QjRy9gblBTTllbXElkWi87KWg3LiElUkMrT0JSS2tWZl9oPCJUUj1LUTIjaCtOLTEobUQ1SmYKaEU0PV5FI0MzWms2cldWOzEtQ1NnT1sqcyIzT18oS3BSWVFsJUVRJmpaYiltIjtSP2s+ITQ9YC9NUiIxJSRuX1gjJHFWdCs2MFNBWlclTjYKX0JhMzlCdU1PPFsna1hoJSclRVlWc2BrdGJkNDdncDJEKilLNFdEPj1yZytPSVIjbipPVkk6XiVqZENOak5iPFpRcUJqZCdKPU0oTSQ1NScKKTltLj0vOyhQQ05iWipxaE8oYFBiV0NgImc3Q3M6NGVQUUhlQU4mLT8qZjdvWmMrRSdvcTdCckhFW3BHXyhsOHVjIWpKXEtCTlBVXDBlPnQKL2pWPE9kJD5iTUZBUmxfLWgqZjVSPikkZFFLO2VvJSRuXlMjK0BIXEtFYSZCLlhZT2hcXmd0SCJqN1pPXC9fNSMiTnE8Ij9pMUtLYExISCEKQXJDNiklMCI0Uj9baScyLSskLH4+CmVuZHN0cmVhbQplbmRvYmoKeHJlZgowIDIyCjAwMDAwMDAwMDAgNjU1MzUgZiAKMDAwMDAwMDAwOSAwMDAwMCBuIAowMDAwMDAwMDU4IDAwMDAwIG4gCjAwMDAwMDAxMDQgMDAwMDAgbiAKMDAwMDAwMDE2MiAwMDAwMCBuIAowMDAwMDAwMjE0IDAwMDAwIG4gCjAwMDAwMDAzMTIgMDAwMDAgbiAKMDAwMDAwMDQxNSAwMDAwMCBuIAowMDAwMDAwNTIxIDAwMDAwIG4gCjAwMDAwMDA2MzEgMDAwMDAgbiAKMDAwMDAwMDcyNyAwMDAwMCBuIAowMDAwMDAwODI5IDAwMDAwIG4gCjAwMDAwMDA5MzQgMDAwMDAgbiAKMDAwMDAwMTA0MyAwMDAwMCBuIAowMDAwMDAxMTQ0IDAwMDAwIG4gCjAwMDAwMDEyNDQgMDAwMDAgbiAKMDAwMDAwMTM0NiAwMDAwMCBuIAowMDAwMDAxNDUyIDAwMDAwIG4gCjAwMDAwMDE2MjIgMDAwMDAgbiAKMDAwMDAwMTk4MiAwMDAwMCBuIAowMDAwMDA1NDY0IDAwMDAwIG4gCjAwMDAwMDU4NDAgMDAwMDAgbiAKdHJhaWxlcgo8PAovSW5mbyAxNyAwIFIKL1NpemUgMjIKL1Jvb3QgMSAwIFIKPj4Kc3RhcnR4cmVmCjczNDQKJSVFT0YK";
			final FedExServiceImpl.Config config = Mockito.mock(FedExServiceImpl.Config.class);
			final MockResponse mockResponse = new MockResponse();
			final Map<String, Object> requestHeader = new HashMap<>();
			final Cookie cookie = new Cookie("JSESSIONID", "213123132");
			final Cookie[] cookieObj = new Cookie[1];
			FedExResponse fedexResponse;
			mockWebServer = new MockWebServer();
			mockResponse.setResponseCode(200);
			mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
			addCookies(mockResponse);
			mockWebServer.enqueue(mockResponse);
			mockWebServer.start(ThreadLocalRandom.current().nextInt(11111, 12111));
			cookie.setComment("HttpOnly");
			cookie.setDomain("pattern");
			cookie.setMaxAge(2);
			cookie.setPath("uri");
			cookie.setSecure(true);
			cookie.setValue("newValue");
			cookie.setVersion(1);
			cookieObj[0] = cookie;
			requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
			requestHeader.put(Constant.STORE_KEY, "AG");
			requestHeader.put(Constant.DOMAIN_KEY, "AG");
			requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
			Mockito.when(config.endPoint())
					.thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
			Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
			impl.activate(config);
			fedexResponse = impl.fetch(requestHeader);
			Assert.assertNotNull(fedexResponse);
			Assert.assertEquals(imageVal, fedexResponse.getImage());
		} finally {
			if (mockWebServer != null)
				mockWebServer.shutdown();
		}
	}

	
	 @Test(expected = ServiceException.class)
	  public void testFedexServiceWithWCSError() throws IOException, ServiceException {
	    try (InputStream is1 = getClass().getResourceAsStream("fedex_request.json")) {
	      final FedExServiceImpl.Config config = Mockito
	          .mock(FedExServiceImpl.Config.class);
	      final MockResponse mockResponse = new MockResponse();
	      final Map<String, Object> requestMap = new HashMap<>();
	      final Cookie cookie = new Cookie("JSESSIONID", "213123132");
	      final Cookie[] cookieObj = new Cookie[1];
	      mockWebServer = new MockWebServer();
	      mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	      addCookies(mockResponse);
	      mockWebServer.enqueue(mockResponse);
	      mockWebServer.start();
	      cookie.setComment("HttpOnly");
	      cookie.setDomain("pattern");
	      cookie.setMaxAge(2);
	      cookie.setPath("uri");
	      cookie.setSecure(true);
	      cookie.setValue("newValue");
	      cookie.setVersion(1);
	      cookieObj[0] = cookie;
	      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
	      requestMap.put(Constant.STORE_KEY, "AG");
	      requestMap.put(Constant.DOMAIN_KEY, "AG");
	      requestMap.put(Constant.PART_NUMBER, "1234");
	      requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
	      Mockito.when(config.endPoint())
	          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
	      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
	      impl.activate(config);
	      impl.fetch(requestMap);

	    } catch (final ServiceException serviceException) {
	      Assert.assertEquals(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR),
	          serviceException.getErrorKey());
	      Assert.assertEquals("Generic Error Ocuured", serviceException.getErrorMessage());
	      throw serviceException;
	    } finally {
	      if (mockWebServer != null)
	        mockWebServer.shutdown();
	    }
	  }
	 
	 @Test(expected = ServiceException.class)
	  public void testFedexServiceWithWCSDown() throws IOException, ServiceException {
	    try (InputStream is1 = getClass().getResourceAsStream("fedex_request.json")) {
	      final FedExServiceImpl.Config config = Mockito
	          .mock(FedExServiceImpl.Config.class);
	      final MockResponse mockResponse = new MockResponse();
	      final Map<String, Object> requestMap = new HashMap<>();
	      final Cookie cookie = new Cookie("JSESSIONID", "213123132");
	      final Cookie[] cookieObj = new Cookie[1];
	      mockWebServer = new MockWebServer();
	      addCookies(mockResponse);
	      mockWebServer.enqueue(mockResponse);
	      mockWebServer.start();
	      cookie.setComment("HttpOnly");
	      cookie.setDomain("pattern");
	      cookie.setMaxAge(2);
	      cookie.setPath("uri");
	      cookie.setSecure(true);
	      cookie.setValue("newValue");
	      cookie.setVersion(1);
	      cookieObj[0] = cookie;
	      requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
	      requestMap.put(Constant.STORE_KEY, "AG");
	      requestMap.put(Constant.DOMAIN_KEY, "AG");
	      requestMap.put(Constant.PART_NUMBER, "1234");
	      requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
	      Mockito.when(config.endPoint())
	          .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
	      Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
	      impl.activate(config);
	      impl.fetch(requestMap);

	    } catch (final ServiceException serviceException) {
	      Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
	      Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
	      throw serviceException;
	    } finally {
	      if (mockWebServer != null)
	        mockWebServer.shutdown();
	    }
	  }
	
	
	private void addCookies(final MockResponse mockResponse) {
		mockResponse.addHeader("Set-Cookie",
				"JSESSIONID=0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq; path=/; domain=localhost; HttpOnly; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
		mockResponse.addHeader("Set-Cookie",
				"WC_SESSION_ESTABLISHED=true; path=/; domain=localhost; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
	}
}
