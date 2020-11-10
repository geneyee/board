package com.boardproject.beans;

public class PageBean {
	
	//최소 페이지 번호
	private int min;
	
	//최대 페이지 번호
	private int max;
	
	//이전 버튼의(이전버튼 누르면 이동하는) 페이지 번호
	private int prevPage;
	
	//다음 버튼의(다음버튼 누르면 이동하는) 페이지 번호
	private int nextPage;
	
	//전체 페이지 개수
	private int pageCnt;
	
	//현재 페이지 번호
	private int currentPage;

	//생성자 생성해서 바로 계산한다.
	//contentCnt : 전체글 개수, currentPage : 현재 글 번호, contentPageCnt : 페이지당 글의 개수, paginationCnt : 페이지 버튼의 개수
	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		
		//현재 페이지 번호
		this.currentPage=currentPage;
		
		//전체 페이지 개수
		pageCnt = contentCnt / contentPageCnt;
		if(contentCnt % contentPageCnt > 0) {
			pageCnt++;
		}
		/*	전체 글 개수에서 페이지 당 글 의 개수를 나누면 그 몫의 값이 페이지의 개수이다.
		 * 	그런데 11개의 글이 있을 경우 페이지당 글 개수를 10으로 설정했을시 나머지 1이 남는다.
		 * 	이것을 다음 페이지로 넘겨야 하므로 나머지가 0보다 클 경우에는 페이지수가 +1하도록 설정한다.	*/
		
		//아래 페이지 버튼 설정. 1누르면 1페이지-10개 2누르면 11페이지-10개
		min = ((currentPage -1) / contentPageCnt) * contentPageCnt + 1;
		max = min + paginationCnt - 1;
		
		if(max > pageCnt) {
			max = pageCnt; //전체 페이지 개수가 max임
		}
		
		prevPage = min - 1;
		nextPage = max + 1;
		
		if(nextPage > pageCnt) {
			nextPage = pageCnt;
		}
		
	}
	
	//getter만 생성
	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	

}
