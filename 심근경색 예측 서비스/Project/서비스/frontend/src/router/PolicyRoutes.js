import { Route,Routes } from 'react-router-dom';

import PrivacyPolicyPage from '../pages/footer/PrivacyPolicyPage';
import TermsPage from '../pages/footer/TermsPage';
import CustomerCenterPage from '../pages/footer/CustomerCenterPage';

// Footer 하단 링크 페이지 라우터
function PolicyRoutes() {
  return (
    <Routes>
      <Route path="/privacy-policy" element={<PrivacyPolicyPage />} />
      <Route path="/terms" element={<TermsPage />} />
      <Route path="/customer-center" element={<CustomerCenterPage />} />
    </Routes>
  );
}

export default PolicyRoutes;
