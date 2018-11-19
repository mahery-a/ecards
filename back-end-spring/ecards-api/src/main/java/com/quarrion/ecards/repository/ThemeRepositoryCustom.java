package com.quarrion.ecards.repository;

import java.util.List;

import com.quarrion.ecards.model.ReviewV2;

public interface ThemeRepositoryCustom {

	void addReviewsForTheme(Long themeId, List<ReviewV2> reviews);

	void addHardcodedReviewsForTheme();

	void playWithEntityManager();

}
