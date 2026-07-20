import '../../styles/layout/Footer.css';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../pages/user/AuthContext';

function Footer() {
  const navigate = useNavigate();
  const { user } = useAuth() || {};

  const handleCustomerCenter = (e) => {
    e.preventDefault();
    if (!user) {
      alert("로그인 후 이용 가능합니다.");
      return;
    }
    navigate("/customer-center");
  };

  return (
    <footer className="footer">
      <span>© 2026 HealthiQ. All rights reserved.</span>

      <div className="footer-links">
        <Link to="/privacy-policy">개인정보처리방침</Link>
        <Link to="/terms">이용약관</Link>
        <a href="/customer-center" onClick={handleCustomerCenter}>고객센터</a>
      </div>
    </footer>
  );
}

export default Footer;