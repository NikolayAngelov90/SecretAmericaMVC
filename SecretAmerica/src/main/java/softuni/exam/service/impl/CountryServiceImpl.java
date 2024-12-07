package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.importJsons.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CountryServiceImpl implements CountryService {

    private static final String IMPORT_JSON_PATH = "C:\\Users\\nikit\\OneDrive\\Desktop\\projects\\SecretAmerica\\SecretAmerica\\src\\main\\resources\\files\\json\\countries.json";

    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ModelMapper modelMapper,
                              ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountryFileContent() throws IOException {
        return Files.readString(Path.of(IMPORT_JSON_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        CountryImportDto[] countryImportDtos = this.gson.fromJson(
                readCountryFileContent(), CountryImportDto[].class);

        for (CountryImportDto countryImportDto : countryImportDtos) {
            if (this.countryRepository.findByName(countryImportDto.getName()).isPresent() ||
                    !this.validationUtil.isValid(countryImportDto)) {
                sb.append("Invalid country").append(System.lineSeparator());
                continue;
            }

            this.countryRepository.saveAndFlush(
                    this.modelMapper.map(countryImportDto, Country.class));

            sb.append(String.format("Successfully imported country %s", countryImportDto.getName()));
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}