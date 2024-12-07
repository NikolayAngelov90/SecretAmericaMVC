package softuni.exam.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.importXmls.VisitorImportDto;
import softuni.exam.models.dto.importXmls.VisitorsImportDto;
import softuni.exam.models.entity.Attraction;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.PersonalData;
import softuni.exam.models.entity.Visitor;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.repository.VisitorRepository;
import softuni.exam.service.VisitorService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VisitorServiceImpl implements VisitorService {

    private static final String IMPORT_XML_PATH = "C:\\Users\\nikit\\OneDrive\\Desktop\\projects\\SecretAmerica\\SecretAmerica\\src\\main\\resources\\files\\xml\\visitors.xml";
    private final VisitorRepository visitorRepository;
    private final AttractionRepository attractionRepository;
    private final CountryRepository countryRepository;
    private final PersonalDataRepository personalDataRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public VisitorServiceImpl(VisitorRepository visitorRepository, AttractionRepository attractionRepository,
                              CountryRepository countryRepository, PersonalDataRepository personalDataRepository,
                              XmlParser xmlParser, ModelMapper modelMapper,
                              ValidationUtil validationUtil) {
        this.visitorRepository = visitorRepository;
        this.attractionRepository = attractionRepository;
        this.countryRepository = countryRepository;
        this.personalDataRepository = personalDataRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.visitorRepository.count() > 0;
    }

    @Override
    public String readVisitorsFileContent() throws IOException {
        return Files.readString(Path.of(IMPORT_XML_PATH));
    }

    @Override
    public String importVisitors() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        VisitorsImportDto visitorsImportDto = this.xmlParser.fromFile(
                IMPORT_XML_PATH, VisitorsImportDto.class);

        for (VisitorImportDto visitorImportDto : visitorsImportDto.getVisitorImportDtos()) {
            String firstName = visitorImportDto.getFirstName();
            String lastName = visitorImportDto.getLastName();

            if (this.visitorRepository.findByFirstNameAndLastName(firstName, lastName).isPresent() ||
                    this.visitorRepository.findByPersonalData_Id(visitorImportDto.getPersonalData()).isPresent() ||
                    !this.validationUtil.isValid(visitorImportDto)) {
                sb.append("Invalid visitor").append(System.lineSeparator());
                continue;
            }

            Visitor visitor = modelMapper.map(visitorImportDto, Visitor.class);

            Country country = this.countryRepository.findCountryById(visitorImportDto.getCountry());
            Attraction attraction = this.attractionRepository.findAttractionById(
                    visitorImportDto.getAttraction());
            PersonalData personalData = this.personalDataRepository.findPersonalDataById(
                    visitorImportDto.getPersonalData());

            visitor.setCountry(country);
            visitor.setAttraction(attraction);
            visitor.setPersonalData(personalData);

            this.visitorRepository.save(visitor);

            sb.append(String.format("Successfully imported visitor %s %s",
                    visitor.getFirstName(), visitor.getLastName()));
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
