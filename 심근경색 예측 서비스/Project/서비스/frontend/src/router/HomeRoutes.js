import React from 'react';
import { Routes,Route } from 'react-router-dom';
import HomePage from '../pages/HomePage';

// 메인 관련 라우터
function HomeRoutes() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
    </Routes>
  );
}

export default HomeRoutes;
