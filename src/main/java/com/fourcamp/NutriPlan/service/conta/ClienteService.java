package com.fourcamp.NutriPlan.service.conta;
import com.fourcamp.NutriPlan.dao.conta.CategoriaClienteDao;
import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.dtos.conta.ClienteDto;
import com.fourcamp.NutriPlan.dtos.meta.MetaDto;
import com.fourcamp.NutriPlan.enuns.CategoriaClienteEnum;
import com.fourcamp.NutriPlan.exception.CategoriaClienteException;
import com.fourcamp.NutriPlan.exception.ClienteException;
import com.fourcamp.NutriPlan.exception.ContaException;
import com.fourcamp.NutriPlan.model.conta.CategoriaClienteEntity;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import com.fourcamp.NutriPlan.utils.Constantes;
import com.fourcamp.NutriPlan.enuns.ObjetivoEnum;
import com.fourcamp.NutriPlan.enuns.TempoEnum;
import com.fourcamp.NutriPlan.exception.ObjetivoException;
import com.fourcamp.NutriPlan.service.meta.MetaService;
import com.fourcamp.NutriPlan.utils.CalculoIdade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
   MetaService metaService;
    @Autowired
    ContaService contaService;
    @Autowired
    ClienteDao clienteDao;
    @Autowired
    CalculoIdade calculoIdade;
    @Autowired
    private CategoriaClienteDao categoriaClienteDao;




    public ClienteDto criarCliente(ClienteDto clienteDto){

        // Buscar a categoria cliente pelo nome
        CategoriaClienteEntity categoriaCliente = categoriaClienteDao.buscarCategoriaPorNome(clienteDto.getCategoria());

        if (categoriaCliente == null) {
            throw new CategoriaClienteException(Constantes.MSG_ERRO_CATEGORIA_CLIENTE + categoriaCliente);
        }


        try {
            ClienteEntity cliente = ClienteEntity.builder()
                    .fkNrIdConta(clienteDto.getIdConta())
                    .dsGenero(clienteDto.getGenero())
                    .nrPeso(clienteDto.getPeso())
                    .nrAltura(clienteDto.getAltura())
                    .dsDataNascimento(clienteDto.getDataNascimento())
                    .nrTmb(clienteDto.getTmb())
                    .nrGet(clienteDto.getGet())
                    .fkNrIdCategoria(categoriaCliente.getNomeCategoria())
                    .build();

            ClienteEntity clienteSalvo = clienteDao.criarConta(cliente);
            return mapearCliente(categoriaCliente, clienteSalvo);
        } catch (Exception e){
            throw new ContaException(Constantes.MSG_ERRO_CLIENTE + e.getMessage());
        }
    }

    private ClienteDto mapearCliente(CategoriaClienteEntity categoriaCliente, ClienteEntity clienteSalvo){
        return ClienteDto.builder()
                .idConta(clienteSalvo.getFkNrIdConta())
                .genero(clienteSalvo.getDsGenero())
                .peso(clienteSalvo.getNrPeso())
                .altura(clienteSalvo.getNrAltura())
                .dataNascimento(clienteSalvo.getDsDataNascimento())
                .tmb(clienteSalvo.getNrTmb())
                .get(clienteSalvo.getNrGet())
                .categoria(String.valueOf(CategoriaClienteEnum.valueOf(categoriaCliente.getNomeCategoria())))
                .build();
    }

    public ClienteEntity buscarClientePorId(int idConta) {
        return clienteDao.buscarClientePorId(idConta);
    }

    public List<ClienteEntity> buscarTodosClientes() {
        try {
            return clienteDao.buscarTodosClientes();
        }catch (Exception e){
            throw new ClienteException(Constantes.MSG_ERRO_LISTAR_CLIENTE);
        }
    }

    public void atualizarCliente(ClienteDto cliente) {
        try {
            clienteDao.atualizarCliente(cliente);
        }catch (Exception e){
            throw new ClienteException(Constantes.MSG_ERRO_ATUALIZAR_CLIENTE + e.getMessage());
        }
    }
//TODO arrumar o método para nova versão....dnv

    public double calcularGETSalvar(String email, Integer idConta,ClienteEntity cliente, MetaDto metaDto) {

        String clienteCategoria = buscarClienteCategoria(cliente.getFkNrIdConta());
        CategoriaClienteEnum categoria = CategoriaClienteEnum.valueOf(clienteCategoria);

        //calculo da taxa metabolica basal
        double tmb = calcularTaxaMetabolica(cliente);
        //calcular a primeira parte do GET baseado na categoria do usuario
        double get = calcularCategoriaGET(tmb, categoria);

        ObjetivoEnum objetivoEnum = ObjetivoEnum.valueOf(metaDto.getIdObjetivo());
        TempoEnum tempoEnum = TempoEnum.valueOf(metaDto.getIdTempo());

        //calcular a segunda parte do GET pela meta(objetivo e tempo) do usuario
        get = calcularObjetivoGET(objetivoEnum, tempoEnum, get, tmb);

        clienteDao.atualizarTMBGET(idConta, tmb, get);
        return get;
    }

    public static double calcularObjetivoGET(ObjetivoEnum objetivoEnum, TempoEnum tempoEnum, double get, double tmb) {
        switch (objetivoEnum) {
            case PERDER_PESO:
                switch (tempoEnum) {
                    case RAPIDO:
                        get -= 1000;
                        break;
                    case MEDIO:
                        get -= 600;
                        break;
                    case LONGO_PRAZO:
                        get -= 400;
                        break;

                }
                break;
            case MANUTENCAO:
                get += 0;
                break;
            case HIPERTROFIA:
                switch (tempoEnum) {
                    case RAPIDO:
                        get += 800;
                        break;
                    case MEDIO:
                        get += 500;
                        break;
                    case LONGO_PRAZO:
                        get += 300;
                        break;
                }
                break;
            default:
                throw new ObjetivoException();
        }
        return get;
    }

    public String buscarClienteCategoria(int idCategoria) {
        return clienteDao.buscarClienteCategoria(idCategoria);
    }

    public double calcularTaxaMetabolica(ClienteEntity cliente) {
        double calculo = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento = LocalDate.parse(cliente.getDsDataNascimento(), dtf);

        int idade = calculoIdade.calcularIdade(dataNascimento);

        if (cliente.getDsGenero().equals('M')) {
            calculo = 66.5 + (13.75 * cliente.getNrPeso()) + (5.003 * (cliente.getNrAltura() * 100)) - (6.75 * idade);
        } else if (cliente.getDsGenero().equals('F')) {
            calculo = 655.1 + (9.563 * cliente.getNrPeso()) + (1.850 * (cliente.getNrAltura() * 100)) - (4.676 * idade);
        }

        return calculo;
    }

    public double calcularCategoriaGET(double tmb, CategoriaClienteEnum categoriaAtividade) {
        double fatorAtividade;

        switch (categoriaAtividade) {
            case NAO_MUITO_ATIVO:
                fatorAtividade = 1.2;
                break;
            case LEVEMENTE_ATIVO:
                fatorAtividade = 1.375;
                break;
            case ATIVO:
                fatorAtividade = 1.55;
                break;
            case BASTANTE_ATIVO:
                fatorAtividade = 1.725;
                break;
            default:
                throw new UnsupportedOperationException("Categoria de atividade desconhecida: " + categoriaAtividade);
        }

        return tmb * fatorAtividade;
    }

    public String criarClienteMeta(String email, MetaDto metaDto) {
        Integer idConta = contaService.getIdContaPorEmail(email);
        MetaDto newMeta = new MetaDto();
        if (!metaService.metaExiste(idConta))
        {
            //setar novos objetivos e tempo do cliente
         newMeta=  metaService.criarMeta(idConta,metaDto);

        }
        else
        {
         newMeta.setIdConta(idConta);
         newMeta.setIdObjetivo(metaDto.getIdObjetivo());
         newMeta.setIdTempo(metaDto.getIdTempo());
        }

       ClienteEntity cliente = buscarClientePorId(idConta);

       double newGet = calcularGETSalvar(email,idConta,cliente,metaDto);

        //atualizar meta diaria
      String metaStr=  metaService.atualizarMetaDiaria(cliente,email,newGet,newMeta);

        return "Meta do cliente: \n"
                + newMeta.getIdObjetivo()+",\n"
                +newMeta.getIdTempo()+"\n"+
                "Meta diaria do cliente: \n"+
                metaStr;
    }


}
