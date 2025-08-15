package business.ConfirmDelete;

public class ConfirmDeleteResponse {
  public final String title;
  public final String header;
  public final String content;
  public ConfirmDeleteResponse(String title, String header, String content) {
    this.title = title; this.header = header; this.content = content;
  }
}
