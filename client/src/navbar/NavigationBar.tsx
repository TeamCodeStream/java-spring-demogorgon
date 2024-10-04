import classNames from 'classnames';
import { NavLink } from 'react-router-dom';
import styles from '/src/navbar/NavigationBar.module.scss';

export const NavigationBar = () => {
  return (
    <nav className={classNames(styles.navbar)} role="navigation">
      <div className={styles['container-fluid']}>
        <NavLink to={'/'} className={styles['navbar-brand']}>
          <span></span>
        </NavLink>
        <ul className={classNames(styles['navbar-nav'], styles.nav)}>
          <li>
            <span className={classNames(styles['fa fa-home'])} aria-hidden="true"></span>
            <NavLink to={'/'}>Home</NavLink>
          </li>

          <li>
            <span className="fa fa-search" aria-hidden="true"></span>
            <NavLink to={'/owners/find'}>Find owners</NavLink>
          </li>

          <li>
            <span className="fa fa-th-list" aria-hidden="true"></span>
            <NavLink to={'/vets'}>Veterinarians</NavLink>
          </li>

          <li>
            <span className="fa exclamation-triangle" aria-hidden="true"></span>
            <NavLink to={'/oups'}>Error</NavLink>
          </li>
        </ul>
      </div>
    </nav>
  );
};
