package kr.go.tech.protection.admin.domain.announcement.dao;

import kr.go.tech.protection.admin.domain.announcement.dto.AnnouncementVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnnouncementDAO {

    int insertAnnouncement(AnnouncementVO.InsertRequest request);

    Integer selectAnnouncementCount(String recruitYear);
}
