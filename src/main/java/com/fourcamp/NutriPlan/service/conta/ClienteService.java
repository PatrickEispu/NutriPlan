package com.fourcamp.NutriPlan.service.conta;
import com.fourcamp.NutriPlan.dao.conta.CategoriaClienteDao;
import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.dtos.conta.ClienteDto;
import com.fourcamp.NutriPlan.enuns.CategoriaClienteEnum;
import com.fourcamp.NutriPlan.exception.CategoriaClienteException;
import com.fourcamp.NutriPlan.exception.ClienteException;
import com.fourcamp.NutriPlan.exception.ContaException;
import com.fourcamp.NutriPlan.model.conta.CategoriaClienteEntity;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import com.fourcamp.NutriPlan.utils.Constantes;
import com.fourcamp.NutriPlan.dto.conta.ClientePrimeiroAcessoDto;
import com.fourcamp.NutriPlan.dto.meta.MetaDto;
import com.fourcamp.NutriPlan.enuns.CategoriaEnum;
import com.fourcamp.NutriPlan.enuns.ObjetivoEnum;
import com.fourcamp.NutriPlan.enuns.TempoEnum;
import com.fourcamp.NutriPlan.exception.ObjetivoException;
import com.fourcamp.NutriPlan.model.conta.Cliente;
import com.fourcamp.NutriPlan.model.meta.MetaEntity;
import com.fourcamp.NutriPlan.model.meta.ObjetivoEntity;
import com.fourcamp.NutriPlan.model.meta.TempoEntity;
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


    public ClientePrimeiroAcessoDto criarCliente(ClientePrimeiroAcessoDto cliente, String email) {

        double get = calcularGETSalvar(email);

        metaService.criarMetaDiaria(cliente,email,get);
        clienteDao.criarCliente(cliente);
        return cliente;
    }

    public ClienteDto criarCliente(ClienteDto clienteDto){

        // Buscar a categoria cliente pelo nome
        CategoriaClienteEntity categoriaCliente = categoriaClienteDao.buscarCategoriaPorNome(clienteDto.getCategoria());

        if (categoriaCliente == null) {
            throw new CategoriaClienteException(Constantes.MSG_ERRO_CATEGORIA_CLIENTE + categoriaCliente);
        }


        try {
            ClienteEntity cliente = ClienteEntity.builder()
                    .idConta(clienteDto.getIdConta())
                    .genero(clienteDto.getGenero())
                    .peso(clienteDto.getPeso())
                    .altura(clienteDto.getAltura())
                    .dataNascimento(clienteDto.getDataNascimento())
                    .tmb(clienteDto.getTmb())
                    .get(clienteDto.getGet())
                    .Categoria(categoriaCliente.getNomeCategoria())
                    .build();

            ClienteEntity clienteSalvo = clienteDao.criarConta(cliente);
            return mapearCliente(categoriaCliente, clienteSalvo);
        } catch (Exception e){
            throw new ContaException(Constantes.MSG_ERRO_CLIENTE + e.getMessage());
        }
    }

    private ClienteDto mapearCliente(CategoriaClienteEntity categoriaCliente, ClienteEntity clienteSalvo){
        return ClienteDto.builder()
                .idConta(clienteSalvo.getIdConta())
                .genero(clienteSalvo.getGenero())
                .peso(clienteSalvo.getPeso())
                .altura(clienteSalvo.getAltura())
                .dataNascimento(clienteSalvo.getDataNascimento())
                .tmb(clienteSalvo.getTmb())
                .get(clienteSalvo.getGet())
                .Categoria(String.valueOf(CategoriaClienteEnum.valueOf(categoriaCliente.getNomeCategoria())))
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

    public void atualizarCliente(ClienteEntity cliente) {
        try {
            clienteDao.atualizarCliente(cliente);
        }catch (Exception e){
            throw new ClienteException(Constantes.MSG_ERRO_ATUALIZAR_CLIENTE + e.getMessage());
        }
    }

    public double calcularGETSalvar(String email) {
        Integer idConta = contaService.getIdContaPorEmail(email);
        Cliente cliente = buscarClientePorId(idConta);
        String categoriaStr = buscarClienteCategoria(cliente.getIdCategoria());
        //TODO arrumar function de pegar categoria por id
        CategoriaEnum categoria = CategoriaEnum.valueOf(categoriaStr);

        //calculo da taxa metabolica basal
        double tmb = calcularTaxaMetabolica(cliente);
        //calcular a primeira parte do GET baseado na categoria do usuario
        double get = calcularCategoriaGET(tmb, categoria);

        MetaEntity meta = metaService.getMeta(idConta);

        ObjetivoEntity objetivo = metaService.getObjetivoPorId(meta.getIdObjetivo());
        ObjetivoEnum objetivoEnum = ObjetivoEnum.valueOf(objetivo.getDsDescricaoObjetivo());

        TempoEntity tempo = metaService.getTempoPorId(meta.getIdTempo());
        TempoEnum tempoEnum = TempoEnum.valueOf(tempo.getDescricaoTempo());


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

    public double calcularTaxaMetabolica(Cliente cliente) {
        double calculo = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento = LocalDate.parse(cliente.getDataNascimento(), dtf);

        int idade = calculoIdade.calcularIdade(dataNascimento);

        if (cliente.getGenero().equals('M')) {
            calculo = 66.5 + (13.75 * cliente.getPeso()) + (5.003 * (cliente.getAltura() * 100)) - (6.75 * idade);
        } else if (cliente.getGenero().equals('F')) {
            calculo = 655.1 + (9.563 * cliente.getPeso()) + (1.850 * (cliente.getAltura() * 100)) - (4.676 * idade);
        }

        return calculo;
    }

    public double calcularCategoriaGET(double tmb, CategoriaEnum categoriaAtividade) {
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

    public MetaDto criarClienteMeta(String email, MetaDto metaDto) {
        //setar novos objetivos e tempo do cliente
      MetaDto metaSalva=  metaService.criarMeta(email,metaDto);

       Integer idConta = contaService.getIdContaPorEmail(email);
       Cliente cliente = buscarClientePorId(idConta);

       double newGet = calcularGETSalvar(email);

        //atualizar meta diaria
        metaService.atualizarMetaDiaria(cliente,email,newGet,metaSalva);

        return metaSalva;
    }
}
