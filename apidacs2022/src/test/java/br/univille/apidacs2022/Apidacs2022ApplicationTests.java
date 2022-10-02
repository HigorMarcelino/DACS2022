package br.univille.apidacs2022;




import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

//import com.fasterxml.jackson.core.JsonpCharacterEscapes;

import br.univille.apidacs2022.api.CidadeControllerApi;
import br.univille.apidacs2022.api.ConsultaControllerApi;
import br.univille.apidacs2022.api.MedicoControllerApi;
import br.univille.apidacs2022.api.PacienteControllerAPI;
import br.univille.apidacs2022.api.PlanoDeSaudeControllerApi;
import br.univille.apidacs2022.api.ProcedimentoControllerApi;

@SpringBootTest
@AutoConfigureMockMvc
class Apidacs2022ApplicationTests {

	@Autowired
	private PacienteControllerAPI pacienteControllerAPI;
	@Autowired
	private CidadeControllerApi cidadeControllerAPI;
	@Autowired
	private ConsultaControllerApi consultaControllerAPI;
	@Autowired
	private MedicoControllerApi medicoControllerAPI;
	@Autowired
	private PlanoDeSaudeControllerApi planoControllerAPI;
	@Autowired
	private ProcedimentoControllerApi procedimentoControllerAPI;
	@Autowired
	private MockMvc mockMvc;

	
	@Test
	void contextLoads() {
		assertThat(pacienteControllerAPI).isNotNull();
		assertThat(cidadeControllerAPI).isNotNull();
		assertThat(consultaControllerAPI).isNotNull();
		assertThat(medicoControllerAPI).isNotNull();
		assertThat(planoControllerAPI).isNotNull();
		assertThat(procedimentoControllerAPI).isNotNull();
	}


	// testes pacientes
	@Test
	void pacienteControllerAPITest() throws Exception{
		MvcResult resultAuth = 
		mockMvc.perform(post("/api/v1/auth/signin")
			.content("{\"user\":\"admin\",\"senha\":\"senha\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		String jwtToken = resultAuth.getResponse().getContentAsString();

		MvcResult result = 
		mockMvc.perform(post("/api/v1/pacientes")
			.content("{\"nome\":\"Teste\",\"sexo\":\"Masculino\"}")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andReturn();
		
		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/pacientes/" + objJson.getString("id"))
			.header("Authorization", "Bearer " + jwtToken))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nome", is("Teste")))
			.andExpect(jsonPath("$.sexo", is("Masculino")));

		mockMvc.perform(put("/api/v1/pacientes/" + objJson.getString("id"))
		.content("{\"id\":\""+objJson.getString("id")+"\", \"nome\":\"Testa\",\"sexo\":\"Masculino\"}")
		.header("Authorization", "Bearer " + jwtToken)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome", is("Testa")))
		.andExpect(jsonPath("$.sexo", is("Masculino")));

		mockMvc.perform(delete("/api/v1/pacientes/" + objJson.getString("id"))
		.header("Authorization", "Bearer " + jwtToken))
		.andExpect(status().isOk());
	}


	// testes medicos
	@Test
	void medicoControllerAPITest() throws Exception{
		MvcResult result = 
		mockMvc.perform(post("/api/v1/medicos")
			.content("{\"nome\":\"Teste\",\"crm\":\"1024\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andReturn();
		
		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/medicos/" + objJson.getString("id")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nome", is("Teste")))
			.andExpect(jsonPath("$.crm", is("1024")));

		mockMvc.perform(put("/api/v1/medicos/" + objJson.getString("id"))
		.content("{\"id\":\""+objJson.getString("id")+"\", \"nome\":\"Testa\",\"crm\":\"1025\"}")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome", is("Testa")))
		.andExpect(jsonPath("$.crm", is("1025")));
	}


	// testes planos
	@Test
	void planoControllerAPITest() throws Exception{
		MvcResult result = 
		mockMvc.perform(post("/api/v1/planos")
			.content("{\"nome\":\"UNIMED\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andReturn();
		
		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/planos/" + objJson.getString("id")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nome", is("UNIMED")));

		mockMvc.perform(put("/api/v1/planos/" + objJson.getString("id"))
		.content("{\"id\":\""+objJson.getString("id")+"\", \"nome\":\"DUOMED\"}")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome", is("DUOMED")));
	}


	// testes cidades
	@Test
	void cidadeControllerAPITest() throws Exception{
		MvcResult result = 
		mockMvc.perform(post("/api/v1/cidades")
			.content("{\"nome\":\"Gravatal\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andReturn();
		
		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/cidades/" + objJson.getString("id")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nome", is("Gravatal")));

		mockMvc.perform(put("/api/v1/cidades/" + objJson.getString("id"))
		.content("{\"id\":\""+objJson.getString("id")+"\", \"nome\":\"Gravata Borboleta\"}")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome", is("Gravata Borboleta")));
	}

	// testes procedimentos
	@Test
	void procedimentoControllerAPITest() throws Exception{
		MvcResult result = 
		mockMvc.perform(post("/api/v1/procedimentos")
			.content("{\"descricao\":\"Teste\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andReturn();
		
		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/procedimentos/" + objJson.getString("id")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.descricao", is("Teste")));

		mockMvc.perform(put("/api/v1/procedimentos/" + objJson.getString("id"))
		.content("{\"id\":\""+objJson.getString("id")+"\", \"descricao\":\"Testa\"}")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.descricao", is("Testa")));
	}

	
	// testes consultas
	@Test
	void consultaControllerAPITest() throws Exception{
		MvcResult result = 
		mockMvc.perform(post("/api/v1/consultas")
			.content("{\"data\":\"2022-09-14T05:30:00.000+00:00\", \"status\":\"Teste\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andReturn();
		
		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/consultas/" + objJson.getString("id")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data", is("2022-09-14")))
			.andExpect(jsonPath("$.status", is("Teste")));

		mockMvc.perform(put("/api/v1/consultas/" + objJson.getString("id"))
		.content("{\"id\":\""+objJson.getString("id")+"\", \"data\":\"2002-01-13T05:30:00.000+00:00\", \"status\":\"Testao\"}")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data", is("2002-01-13T05:30:00.000+00:00")))
		.andExpect(jsonPath("$.status", is("Testao")));
	}
	
}
