package ndtp.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import ndtp.domain.CivilVoiceComment;

@Repository
public interface CivilVoiceCommentMapper {
	
	/**
	 * 시민 참여 댓글 목록 조회
	 * @param CivilVoiceId
	 * @return
	 */
	List<CivilVoiceComment> getListCivilVoiceComment(Long CivilVoiceId);
	
	/**
	 * 등록
	 * @param civilVoiceComment
	 * @return
	 */
	int insertCivilVoiceComment(CivilVoiceComment civilVoiceComment);
	
	/**
	 * 수정
	 * @param civilVoiceComment
	 * @return
	 */
	int updateCivilVoiceComment(CivilVoiceComment civilVoiceComment);
	
	/**
	 * 삭제 
	 * @param civilVoiceComment
	 * @return
	 */
	int deleteCivilVoiceComment(CivilVoiceComment civilVoiceComment);
}
