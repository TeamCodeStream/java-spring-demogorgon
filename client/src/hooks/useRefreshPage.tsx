import { useNavigate } from 'react-router-dom';

const useRefreshPage = (navigateTo = '') => {
  const navigate = useNavigate();
  return () => {
    if (navigateTo) {
      navigate(navigateTo);
    }
    navigate(0);
  };
};

export default useRefreshPage;
