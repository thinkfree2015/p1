package com.ming800.organization.service.impl;

import com.ming800.core.xdo.dao.XdoDao;
import com.ming800.organization.OrganizationConst;
import com.ming800.organization.model.Branch;
import com.ming800.organization.model.User;
import com.ming800.organization.service.BranchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-16
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BranchManagerImpl implements BranchManager {

    @Autowired
    private XdoDao xdoDao;

    @Override
    public void saveOrUpdate(Branch branch) {
        xdoDao.saveOrUpdateObject(Branch.class.getName(), branch);
    }

    @Override
    public Branch getBranch(String branchId) {
        return (Branch) xdoDao.getObject(Branch.class.getName(), branchId);
    }

    @Override
    public Branch getBranchByName(String name) throws Exception {
        Branch branch = null;
        String queryStr = "from Branch b where b.name = ? and b.theStatus =" + OrganizationConst.BRANCH_THE_STATUS_NORMAL;
        List<Branch> branchList = (List<Branch>) xdoDao.getObjectList(queryStr, name);
        if (branchList != null && branchList.size() > 0) {
            if (branchList.size() == 1) {
                branch = branchList.get(0);
            } else {
                throw new Exception("机构名冲突异常");
            }
        } else {
            //这里先暂时注释掉
            // throw new Exception("机构不存在");
        }
        return branch;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Branch> listBranch() {
        String queryStr = "select b from Branch b where b.theStatus = 6";
        return (List<Branch>) xdoDao.getObjectList(queryStr);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Branch checkBranchName(String branchName) {
        String queryStr = "from Branch b where b.name = ?";          // and b.theStatus in (1,2,3)
        List<Branch> branchList = (List<Branch>) xdoDao.getObjectList(queryStr, branchName);
        if (branchList != null && branchList.size() > 0) {
            return branchList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User checkUsername(String username) {
        String queryStr = "from User b where b.username = ? and b.theStatus!=0";          // and b.theStatus in (1,2,3)
        List<User> userList = (List<User>) xdoDao.getObjectList(queryStr, username);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }
}
