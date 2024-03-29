package com.rss;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedOutput;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RssBuildFactory {
	private SyndFeed feed;
	@SuppressWarnings("unchecked")
	private List entries;
	private SyndEntry entry;

	@SuppressWarnings("unchecked")
	public RssBuildFactory() {
		feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		entries = new ArrayList();
	}

	/**
	 * 创建一个频道
	 * 
	 * @param title
	 *            　频道标题
	 * @param link
	 *            　频道对应的连接
	 * @param description
	 *            　频道描述
	 * @param language
	 *            　频道所用语言
	 * @param pubDate
	 *            　频道发布时期
	 * @param copyright
	 *            　版权所有
	 * @throws Exception
	 */
	public void buildChannel(String title, String link, String description,
                             String language, Date pubDate, String copyright)
			throws RuntimeException {
		feed.setTitle(title);
		feed.setLink(link);
		feed.setDescription(description);
		feed.setLanguage(language);
		feed.setPublishedDate(pubDate);
		feed.setCopyright(copyright);
	}

	/**
	 * 添加频道的子内容
	 * 
	 * @param item
	 *            [email={@link]{@link[/email] ChannelItem}
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void buildItems(ChannelItem item) throws RuntimeException {
		entry = new SyndEntryImpl();
		// 设置新闻标题
		entry.setTitle(item.getTitle());
		// 设置新闻的连接地址
		entry.setLink(item.getLink());
		// 设置新闻简介
		SyndContent content = new SyndContentImpl();
		content.setType("text/plain");
		content.setValue(item.getDescription());
		entry.setDescription(content);
		// 设置发布时间
		entry.setPublishedDate(item.getPubDate());
		// 设置频道所属的范围
		SyndCategory cate = new SyndCategoryImpl();
		cate.setName(item.getCategory());
		
		List cateList = new ArrayList();
		cateList.add(cate);
		entry.setCategories(cateList);
		// 设置作者
		entry.setAuthor(item.getAuthor());
		// 将新闻项添加至数组中
		entries.add(entry);
	}

	/**
	 * 添加频道的内容项
	 * 
	 * @param item
	 *            [email={@link]{@link[/email] ChannelEItem}此类继承自ChannelItem类
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void buildItems(ChannelEItem item) throws RuntimeException {
		entry = new SyndEntryImpl();
		// 设置新闻标题
		entry.setTitle(item.getTitle());
		// 设置新闻的连接地址
		entry.setLink(item.getLink());
		// 设置新闻简介
		SyndContent content = new SyndContentImpl();
		content.setValue(item.getDescription());
		entry.setDescription(content);
		// 设置发布时间
		entry.setPublishedDate(item.getPubDate());
		// 设置频道所属的范围
		SyndCategory cate = new SyndCategoryImpl();
		cate.setName(item.getCategory());
		
		List cateList = new ArrayList();
		cateList.add(cate);
		entry.setCategories(cateList);
		// 设置作者
		entry.setAuthor(item.getAuthor());
		// 设置流媒体播放文件
		SyndEnclosure en = new SyndEnclosureImpl();
		en.setUrl(item.getEnclosure());
		
		List enList = new ArrayList();
		enList.add(en);
		entry.setEnclosures(enList);
		// 将新闻项添加至数组中
		entries.add(entry);
	}

	/**
	 * 生成XML文件
	 * 
	 * @param filePath
	 *            　文件保存路径和名称
	 * @throws Exception
	 */
	public void buildChannel(String filePath) throws Exception {
		feed.setEntries(entries);
		SyndFeedOutput output = new SyndFeedOutput();
		Writer writer;
		writer = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
		output.output(feed, writer);
	}

}
