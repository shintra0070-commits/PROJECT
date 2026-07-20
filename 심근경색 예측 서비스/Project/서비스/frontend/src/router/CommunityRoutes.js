import React from 'react';

import {Routes,Route} from 'react-router-dom';

import CommunityPage from '../pages/community/CommunityPage';
import CommunityDetailPage from '../pages/community/CommunityDetailPage';
import CommunityWritePage from '../pages/community/CommunityWritePage';
import CommunityEditPage from '../pages/community/CommunityEditPage';

function CommunityRoutes() {

  return (

    <Routes>
      <Route path="/community" element={<CommunityPage />}/>
      <Route path="/community/write" element={<CommunityWritePage />}/>
      <Route path="/community/edit/:postId" element={<CommunityEditPage />}/>
      <Route path="/community/:postId" element={<CommunityDetailPage />}/>

    </Routes>
  );
}

export default CommunityRoutes;