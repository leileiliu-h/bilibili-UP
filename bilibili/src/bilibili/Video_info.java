package bilibili;

public class Video_info {

	String play;
	String pic;
	String title;
	String comment;
	String favorites;
	public Video_info(String play, String pic, String title, String comment, String favorites) {
		super();
		this.play = play;
		this.pic = pic;
		this.title = title;
		this.comment = comment;
		this.favorites = favorites;
	}
	
	public String getPlay() {
		return play;
	}
	public void setPlay(String play) {
		this.play = play;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFavorites() {
		return favorites;
	}
	public void setFavorites(String favorites) {
		this.favorites = favorites;
	}
	
	@Override
	public String toString() {
		return "Video_info [play=" + play + ", pic=" + pic + ", title=" + title + ", comment=" + comment
				+ ", favorites=" + favorites + "]";
	}
	
	
	
}
