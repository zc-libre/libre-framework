package com.libre.framework.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.libre.framework.blog.mapper.ArticleMapper;
import com.libre.framework.blog.pojo.Article;
import com.libre.framework.blog.pojo.vo.Statistic;
import com.libre.framework.blog.service.CategoryService;
import com.libre.framework.blog.service.StatisticService;
import com.libre.framework.blog.service.TagService;
import com.libre.framework.common.constant.LibreConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

	private final ArticleMapper articleMapper;

	private final TagService tagService;

	private final CategoryService categoryService;

	@Override
	public Statistic statistic() {
		LambdaQueryWrapper<Article> articleWrapper = Wrappers.<Article>lambdaQuery()
			.eq(Article::getStatus, LibreConstants.ENABLE)
			.eq(Article::getIsAbout, LibreConstants.NO);
		Long articles = articleMapper.selectCount(articleWrapper);

		Long tags = tagService.count();
		Long categories = categoryService.count();

		Statistic statistic = new Statistic();
		statistic.setArticles(articles);
		statistic.setTags(tags);
		statistic.setCategories(categories);
		return statistic;
	}

}
