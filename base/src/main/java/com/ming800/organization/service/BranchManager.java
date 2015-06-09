package com.ming800.organization.service;

import com.ming800.organization.model.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-16
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public interface BranchManager {
    void saveOrUpdate(Branch branch);

    Branch getBranch(String branchId);

    Branch getBranchByName(String name) throws Exception;

    List<Branch> listBranch();

    /**
     * 检查学校表示的name是否存在
     *
     * @param branchName branch.name
     * @return
     */
    Branch checkBranchName(String branchName);

    User checkUsername(String username);

}
