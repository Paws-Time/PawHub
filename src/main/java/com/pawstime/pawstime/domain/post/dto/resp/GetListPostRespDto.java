package com.pawstime.pawstime.domain.post.dto.resp;

import com.pawstime.pawstime.domain.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetListPostRespDto(
        Long id,                 // 게시글 ID
        String title,            // 게시글 제목
        String contentPreview,   // 내용 미리보기
        LocalDateTime createdAt, // 작성일
        LocalDateTime updatedAt, // 수정일
        int views,               // 조회수
        int likesCount,          // 좋아요 수
        String category          // 게시글 카테고리
) {
    // Post 엔티티를 DTO로 변환하는 메서드
    public static GetListPostRespDto from(Post post) {
        return GetListPostRespDto.builder()
                .id(post.getPostId())                        // 게시글 ID
                .title(post.getTitle())                      // 게시글 제목
                .contentPreview(post.getContent().length() > 100
                        ? post.getContent().substring(0, 100) + "..." // 내용 미리보기
                        : post.getContent())
                .createdAt(post.getCreatedAt())              // 작성일
                .updatedAt(post.getUpdatedAt())              // 수정일
                .views(post.getViews())                      // 조회수
                .likesCount(post.getLikesCount())            // 좋아요 수
                .category(post.getCategory() != null ? post.getCategory().name() : "") // 카테고리
                .build();
    }
}