package com.devsuperior.movieflix.services;

import static com.devsuperior.movieflix.constants.Constants.MOVIE_NOT_FOUND;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.dto.MovieCardDTO;
import com.devsuperior.movieflix.entities.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.dto.ReviewDTO;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Optional<Movie> dto = movieRepository.findById(id);
        Movie entity = dto.orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND));
        return new MovieDetailsDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findAllPageMovieByGenre(Long genreId, Pageable pageable) {
        Genre genre = (genreId == 0) ? null : genreRepository.getReferenceById(genreId);
        Page<Movie> movies = movieRepository.findMovieByGenre(genre, pageable);
        return movies.map(x -> new MovieCardDTO(x));
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findByReviewMovieId(Long id) {
        List<Review> reviews = reviewRepository.findByReviewMovieId(id);
        return reviews.stream().map(x -> new ReviewDTO(x)).toList();
    }
}
