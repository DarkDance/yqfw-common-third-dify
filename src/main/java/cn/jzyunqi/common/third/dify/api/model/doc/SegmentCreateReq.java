package cn.jzyunqi.common.third.dify.api.model.doc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.text.Segment;
import java.util.List;

/**
 * @author wiiyaya
 * @since 2025/3/2
 */
@Getter
@Setter
@ToString
public class SegmentCreateReq {
    private List<SegmentParam> segments;
}
