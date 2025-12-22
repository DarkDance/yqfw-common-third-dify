package cn.jzyunqi.common.third.dify.api;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.dify.api.model.doc.DatasetData;
import cn.jzyunqi.common.third.dify.api.model.doc.DatasetParam;
import cn.jzyunqi.common.third.dify.api.model.doc.DocData;
import cn.jzyunqi.common.third.dify.api.model.doc.DocFileData;
import cn.jzyunqi.common.third.dify.api.model.doc.DocParam;
import cn.jzyunqi.common.third.dify.api.model.doc.DocRsp;
import cn.jzyunqi.common.third.dify.api.model.doc.RetrieveParam;
import cn.jzyunqi.common.third.dify.api.model.doc.RetrieveRsp;
import cn.jzyunqi.common.third.dify.api.model.doc.SegmentCreateReq;
import cn.jzyunqi.common.third.dify.api.model.doc.SegmentRsp;
import cn.jzyunqi.common.third.dify.common.DifyHttpExchange;
import cn.jzyunqi.common.third.dify.common.model.DifyPageRsp;
import cn.jzyunqi.common.third.dify.common.model.DifyRspV1;
import cn.jzyunqi.common.third.dify.common.model.DifyRspV2;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * @author wiiyaya
 * @since 2025/3/2
 */
@DifyHttpExchange
@HttpExchange(url = "{scheme}://{host}:{port}/{path}", contentType = "application/json; charset=utf-8", accept = {"application/json"})
public interface DifyDatasetApiProxy {

    //创建空知识库
    @PostExchange(url = "/datasets")
    DatasetData createEmptyDataset(@RequestPart DatasetParam data, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //知识库列表
    @GetExchange(url = "/datasets")
    DifyPageRsp<DatasetData> getDatasetList(@RequestParam Integer page, @RequestParam Integer limit, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //删除知识库
    @DeleteExchange(url = "/datasets/{datasetId}")
    DifyRspV1 deleteDataset(@PathVariable String datasetId, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //检索知识库
    @PostExchange(url = "/datasets/{datasetId}/retrieve")
    RetrieveRsp retrieve(@PathVariable String datasetId, @RequestBody RetrieveParam retrieveParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //通过文本创建文档
    @PostExchange(url = "/datasets/{datasetId}/document/create-by-text")
    DocRsp createDocByText(@PathVariable String datasetId, @RequestBody DocParam docParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //通过文本更新文档
    @PostExchange(url = "/datasets/{datasetId}/documents/{documentId}/update-by-text")
    DocRsp updateDocByText(@PathVariable String datasetId, @PathVariable String documentId, @RequestBody DocParam docParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //通过文件创建文档
    @PostExchange(url = "/datasets/{datasetId}/document/create-by-file", contentType = "multipart/form-data")
    DocRsp createDocByFile(@PathVariable String datasetId, @RequestPart DocParam data, @RequestPart Resource file, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //获取上传文件
    @GetExchange(url = "/datasets/{datasetId}/documents/{documentId}/upload-file")
    DocFileData getDocFile(@PathVariable String datasetId, @PathVariable String documentId, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //通过文件更新文档
    @PostExchange(url = "/datasets/{datasetId}/documents/{documentId}/update-by-file", contentType = "multipart/form-data")
    DocRsp updateDocByFile(@PathVariable String datasetId, @PathVariable String documentId, @RequestPart DocParam data, @RequestPart Resource file, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //获取文档嵌入状态（进度）
    @GetExchange(url = "/datasets/{datasetId}/documents/{batch}/indexing-status")
    DocRsp getDocIndexingStatus(@PathVariable String datasetId, @PathVariable String batch, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //删除文档
    @DeleteExchange(url = "/datasets/{datasetId}/documents/{documentId}")
    DocRsp deleteDoc(@PathVariable String datasetId, @PathVariable String documentId, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //知识库文档列表
    @GetExchange(url = "/datasets/{datasetId}/documents")
    DifyPageRsp<DocData> getDocList(@PathVariable String datasetId, @RequestParam String keyword, @RequestParam Integer page, @RequestParam Integer limit, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //新增分段
    @PostExchange(url = "/datasets/{datasetId}/documents/{documentId}/segments")
    SegmentRsp createDocSegment(@PathVariable String datasetId, @PathVariable String documentId, @RequestBody SegmentCreateReq segmentParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //更新文档分段
    @PostExchange(url = "/datasets/{datasetId}/documents/{documentId}/segments/{segmentId}")
    SegmentRsp updateDocSegment(@PathVariable String datasetId, @PathVariable String documentId, @RequestParam String segmentId, @RequestBody SegmentCreateReq segmentParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //查询文档分段
    @GetExchange(url = "/datasets/{datasetId}/documents/{documentId}/segments")
    SegmentRsp getDocSegmentList(@PathVariable String datasetId, @PathVariable String documentId, @RequestParam String keyword, @RequestParam String status, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //删除文档分段
    @DeleteExchange(url = "/datasets/{datasetId}/documents/{documentId}/segments/{segmentId}")
    DifyRspV2 deleteDocSegment(@PathVariable String datasetId, @PathVariable String documentId, @RequestParam String segmentId, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;
}
