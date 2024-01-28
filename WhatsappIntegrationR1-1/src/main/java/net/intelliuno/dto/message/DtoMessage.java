package net.intelliuno.dto.message;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoMessage {

	   private String id;
	    private String chat_message_type;
	    private String channel_failure_reason;
	    private String message_status;
	    private String received_at_utc;
	    private String delivered_at_utc;
	    private String seen_at_utc;
	    private String campaign_id;
	    public boolean is_template_message;
	    private String raw_template;
	    private String channel_error_code;
	    private String message_content_type;
	    private String media_url;
	    private String message;
	    private Map<String, Object> meta_data;
	
}
