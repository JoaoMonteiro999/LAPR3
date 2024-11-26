package USLP02_08.dataAccess;

public class Repositories {

    private static final Repositories instance = new Repositories();
    private ParcelaRepository parcelaRepository = null;
    private PlantaRepository plantaRepository = null;
    private OperacoesRepository operacoesRepository  = null;
    private CulturaRepository culturaRepository = null;
    private FatorRepository fatorRepository = null;
    private ModosRepository modosRepository = null;



    private Repositories() {
        parcelaRepository = new ParcelaRepository();
        plantaRepository = new PlantaRepository();
        operacoesRepository = new OperacoesRepository();
        culturaRepository = new CulturaRepository();
        fatorRepository = new FatorRepository();
        modosRepository = new ModosRepository();
    }

    public static Repositories getInstance() {
        return instance;
    }
    public ParcelaRepository getParcelaRepository() {
        return parcelaRepository;
    }
    public PlantaRepository getPlantaRepository() {
        return plantaRepository;
    }
    //Registar uma operação de monda no Campo novo, em 08/09/2023, na plantação de cenouras Danvers Half Long, 0.5 ha
    //Registar uma operação de monda no Campo novo, em 08/10/2023, na plantação de cenouras Danvers Half Long, 0.5 ha
    public OperacoesRepository getOperacoesRepository() {
        return operacoesRepository;
    }
    public CulturaRepository getCulturaRepository() { return culturaRepository; }

    public FatorRepository getFatorRepository() {
        return fatorRepository;
    }

    public ModosRepository getModosRepository() {
        return modosRepository;
    }
}
