/*
 * Copyright (c) 2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.scim.common.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.mgt.claim.Claim;
import org.wso2.carbon.identity.mgt.exception.IdentityStoreException;
import org.wso2.carbon.identity.mgt.model.UserModel;
import org.wso2.carbon.identity.mgt.store.IdentityStore;
import org.wso2.carbon.identity.scim.common.utils.AttributeMapper;
import org.wso2.charon.core.v2.exceptions.*;
import org.wso2.charon.core.v2.extensions.UserManager;
import org.wso2.charon.core.v2.objects.Group;
import org.wso2.charon.core.v2.objects.User;
import org.wso2.charon.core.v2.utils.codeutils.Node;
import org.wso2.charon.core.v2.utils.codeutils.SearchRequest;

import java.util.*;

public class SCIMUserManager implements UserManager {
    private static Log log = LogFactory.getLog(SCIMUserManager.class);

    private org.wso2.carbon.identity.mgt.bean.User userStoreUser = null;
    IdentityStore identityStore = null;

    public SCIMUserManager(org.wso2.carbon.identity.mgt.bean.User user, IdentityStore identityStore) {
       this.userStoreUser = user;
       this.identityStore = identityStore;
    }

    @Override
    public User createUser(User user) throws CharonException, ConflictException, BadRequestException {
        try {
            //Persist in carbon userStoreUser store
            if (log.isDebugEnabled()) {
                log.debug("Creating userStoreUser: " + user.getUserName());
            }

            Map<String, String> claimsMap = AttributeMapper.getClaimsMap(user);

            UserModel userModel = getUserModelFromClaims(claimsMap);

            identityStore.addUser(userModel);

            log.info("User: " + user.getUserName() + " is created through SCIM.");

        } catch (IdentityStoreException e) {
            String errMsg = "Error in adding the userStoreUser: " + user.getUserName() + " to the userStoreUser store. ";
            errMsg += e.getMessage();
            throw new CharonException(errMsg, e);
        }
        return user;
    }

    @Override
    public User getUser(String s) throws CharonException, BadRequestException {
        return null;
    }

    @Override
    public void deleteUser(String s) throws NotFoundException, CharonException, NotImplementedException, BadRequestException {

    }

    @Override
    public List<Object> listUsersWithGET(Node node, int i, int i1, String s, String s1) throws CharonException, NotImplementedException, BadRequestException {
        return null;
    }

    @Override
    public List<Object> listUsersWithPost(SearchRequest searchRequest) throws CharonException, NotImplementedException, BadRequestException {
        return null;
    }

    @Override
    public User updateUser(User user) throws NotImplementedException, CharonException, BadRequestException {
        return null;
    }

    @Override
    public User getMe(String s) throws CharonException, BadRequestException, NotFoundException {
        return null;
    }

    @Override
    public User createMe(User user) throws CharonException, ConflictException, BadRequestException {
        return null;
    }

    @Override
    public void deleteMe(String s) throws NotFoundException, CharonException, NotImplementedException, BadRequestException {

    }

    @Override
    public User updateMe(User user) throws NotImplementedException, CharonException, BadRequestException {
        return null;
    }

    @Override
    public Group createGroup(Group group) throws CharonException, ConflictException, NotImplementedException, BadRequestException {
        return null;
    }

    @Override
    public Group getGroup(String s) throws NotImplementedException, BadRequestException, CharonException {
        return null;
    }

    @Override
    public void deleteGroup(String s) throws NotFoundException, CharonException, NotImplementedException, BadRequestException {

    }

    @Override
    public List<Object> listGroupsWithGET(Node node, int i, int i1, String s, String s1) throws CharonException, NotImplementedException, BadRequestException {
        return null;
    }

    @Override
    public Group updateGroup(Group group, Group group1) throws NotImplementedException, BadRequestException, CharonException {
        return null;
    }

    @Override
    public List<Object> listGroupsWithPost(SearchRequest searchRequest) throws NotImplementedException, BadRequestException, CharonException {
        return null;
    }

    private UserModel getUserModelFromClaims(Map<String, String> claims){
        UserModel userModel = new UserModel();
        for(String claim : claims.keySet()){
            Claim newClaim = new Claim();
            newClaim.setClaimURI(claim);
            newClaim.setValue(claims.get(claim));
            //TODO: Add the claim dialect
            newClaim.setDialectURI(claims.get(claim));
        }
        return  userModel;
    }

}

