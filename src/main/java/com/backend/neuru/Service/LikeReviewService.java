package com.backend.neuru.Service;

import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.Entity.LikeEntity;
import com.backend.neuru.Entity.ReviewEntity;
import com.backend.neuru.Entity.WalkwayEntity;
import com.backend.neuru.Repository.LikeRepository;
import com.backend.neuru.Repository.ReviewRepository;
import com.backend.neuru.Repository.WalkwayRepository;
import com.backend.neuru.exception.CustomException;
import com.backend.neuru.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeReviewService {
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;
    private final WalkwayRepository walkwayRepository;

    @Transactional
    public ResponseDTO<?> registerReview(String reviewContents) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReview_content(reviewContents);
        reviewRepository.save(reviewEntity);
        return ResponseDTO.success("산책로 리뷰 등록 완료", reviewEntity);
    }

    @Transactional
    public ResponseDTO<?> registerLike(Long id) {
        LikeEntity likeEntity = new LikeEntity();
        WalkwayEntity walkway = walkwayRepository.findById(id).get();

        likeEntity.setWalkway(walkway);
        likeRepository.save(likeEntity);
        return ResponseDTO.success("산책로 좋아요 등록 완료", likeEntity);
    }

    @Transactional
    public ResponseDTO<?> deleteLike(Long id) {
        WalkwayEntity walkway = walkwayRepository.findById(id).get();
        Optional<LikeEntity> likeEntity = likeRepository.findByWalkway(walkway);
        if (likeEntity.isPresent()) {
            likeRepository.delete(likeEntity.get());
            return ResponseDTO.success("산책로 좋아요 삭제 완료", likeEntity);
        } else{
            throw new CustomException(ErrorCode.WALKWAY_LIKE_NOT_FOUND);
        }

    }

}
