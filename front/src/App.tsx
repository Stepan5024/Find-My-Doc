import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from 'react-router-dom'; // Добавлен useLocation
import { Header } from './components/Header/Header';
import { Sidebar } from './components/Sidebar/Sidebar';
import { ProjectsPage } from './components/ProjectsPage/ProjectsPage';
import { NewProjectPage } from './components/NewProjectPage/NewProjectPage';
import { SettingsUsersPage } from './components/SettingsUsersPage/SettingsUsersPage';
import { Footer } from './components/Footer/Footer';

// Компонент для обертки и передачи currentSection в Header
const AppContent = () => {
  const [isSidebarCollapsed, setIsSidebarCollapsed] = useState(false);
  const location = useLocation();

  const toggleSidebar = () => {
    setIsSidebarCollapsed(!isSidebarCollapsed);
  };

  let currentSection = 'Недавние проекты';
  if (location.pathname === '/projects/new') {
    currentSection = 'Новый проект';
  } else if (location.pathname === '/settings/users') {
    currentSection = 'Настройки команды';
  }

  return (
    <div className="relative flex flex-col h-screen">
      {/* Header */}
      <Header
        isSidebarCollapsed={isSidebarCollapsed}
        toggleSidebar={toggleSidebar}
        currentSection={currentSection}
      />

      {/* Sidebar */}
      <div className={`${isSidebarCollapsed ? 'w-16' : 'w-64'} absolute left-0 top-0 bottom-0`}>
        <Sidebar isCollapsed={isSidebarCollapsed} toggleSidebar={toggleSidebar} />
      </div>

      {/* Основное содержимое */}
      <div
        className={`flex-1 overflow-auto ${
          isSidebarCollapsed ? 'pl-16' : 'pl-64'
        } transition-all duration-300`}
      >
        <Routes>
          <Route path="/" element={<Navigate to="/projects" />} />
          <Route path="/projects" element={<ProjectsPage />} />
          <Route path="/projects/new" element={<NewProjectPage />} />
          <Route path="/settings/users" element={<SettingsUsersPage />} />
        </Routes>
      </div>

      {/* Footer */}
      <Footer isSidebarCollapsed={isSidebarCollapsed} />
    </div>
  );
};

export const App = () => {
  return (
    <Router>
      <AppContent /> {/* Рендерим AppContent внутри Router */}
    </Router>
  );
};