package com.techno.fromKt.service.impl;

import com.techno.fromKt.domain.dto.request.ReqGenreDto;
import com.techno.fromKt.domain.dto.response.ResGenreDto;
import com.techno.fromKt.domain.dto.response.ResMessageDto;
import com.techno.fromKt.domain.entity.GenreEntity;
import com.techno.fromKt.repository.GenreRepository;
import com.techno.fromKt.repository.TypeRepository;
import com.techno.fromKt.repository.UserRepository;
import com.techno.fromKt.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    UserRepository userRepository;
    TypeRepository typeRepository;
    GenreRepository genreRepository;

    @Override
    public ResMessageDto<String> create(ReqGenreDto req) {
        GenreEntity usedGenre = genreRepository.findByName(req.getName());
        if (usedGenre != null) {
            throw new IllegalArgumentException("Genre name already");
        }
        GenreEntity input = GenreEntity.builder()
                .name(req.getName())
                .build();
        try{
            GenreEntity data = genreRepository.save(input);
            String message = data.getId() > 0 ? "Genre added!" : "";
            return new ResMessageDto<>(
                    200,
                    message,
                    null
            );
        } catch (Exception ex){
            throw new IllegalArgumentException("Genre add failed: " + ex);
        }

    }

    @Override
    public ResMessageDto<String> update(int id, ReqGenreDto req) {
        Optional<GenreEntity> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isEmpty()) {
            throw new IllegalArgumentException("Genre not found with ID: " + id);
        }

        GenreEntity genreToUpdate = optionalGenre.get();
        genreToUpdate.setName(req.getName());
        try{
            genreRepository.save(genreToUpdate);
            return new ResMessageDto<>(200, "Genre updated successfully", null);
        } catch (Exception ex){
            throw new IllegalArgumentException("Genre update failed: " + ex);
        }
    }

    @Override
    public ResMessageDto<List<ResGenreDto>> getAll() {
        try{
            List<GenreEntity> allGenres = genreRepository.findAll();
            List<ResGenreDto> resGenreDtoList = allGenres.stream()
                    .map(genre -> ResGenreDto.builder()
                            .genreName(genre.getName())
                            .build())
                    .collect(Collectors.toList());
            return new ResMessageDto<>(200, "List of genres retrieved successfully", resGenreDtoList);
        } catch (Exception ex){
            throw new IllegalArgumentException("Get genres failed: " + ex);
        }

    }

    public ResMessageDto<ResGenreDto> getById(int id) {
        try{
            Optional<GenreEntity> optionalGenre = genreRepository.findById(id);

            if (optionalGenre.isEmpty()) {
                throw new IllegalArgumentException("Genre not found with ID: " + id);
            }

            GenreEntity genre = optionalGenre.get();
            ResGenreDto resGenreDto = ResGenreDto.builder()
                    .genreName(genre.getName())
                    // Isi properti lain yang masih null dengan nilai default atau sesuai kebutuhan
                    .build();

            return new ResMessageDto<>(200, "Genre retrieved successfully", resGenreDto);
        } catch (Exception ex){
            throw new IllegalArgumentException("Get genre failed: " + ex);
        }
    }

    @Override
    public ResMessageDto<String> delete(int id) {
        try{
            GenreEntity genreToDelete = genreRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Genre not found with ID: " + id));

            genreRepository.delete(genreToDelete);

            return new ResMessageDto<>(200, "Genre deleted successfully", null);
        } catch (Exception ex){
            throw new IllegalArgumentException("Genre deletion error: " + ex);
        }
    }
}
