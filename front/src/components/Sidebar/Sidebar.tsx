import { useState, useEffect, useRef } from 'react';
import { motion } from 'framer-motion';
import { SidebarHeader } from './SidebarHeader';
import { DropdownMenu } from './DropdownMenu';
import { SidebarContent } from './SidebarContent';
import { CollapsedSidebar } from './CollapsedSidebar';
import { SidebarFooter } from './SidebarFooter'; // Импорт SidebarFooter

interface SidebarProps {
  isCollapsed: boolean;
  toggleSidebar: () => void;
}

export const Sidebar = ({ isCollapsed, toggleSidebar }: SidebarProps) => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isHovered, setIsHovered] = useState(false);
  const [textBottom, setTextBottom] = useState(0); // Состояние для хранения нижнего края текста
  const dropdownRef = useRef<HTMLDivElement>(null);

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const closeDropdown = () => {
    setIsDropdownOpen(false);
  };

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        closeDropdown();
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <motion.aside
      className={`bg-yellow-100 ${isCollapsed ? 'w-16' : 'w-64'} transition-all duration-300 h-screen flex flex-col`} // Добавлен bg-gray-100 для фона
      initial={{ x: -100 }}
      animate={{ x: 0 }}
      transition={{ duration: 0.5 }}
    >
      {!isCollapsed ? (
        <div className="flex-1 flex flex-col">
          {/* Header */}
          <div className="px-4 pt-0 pb-4"> 
            <SidebarHeader
              isDropdownOpen={isDropdownOpen}
              toggleDropdown={toggleDropdown}
              toggleSidebar={toggleSidebar}
              isHovered={isHovered}
              setIsHovered={setIsHovered}
              isCollapsed={isCollapsed} 
              onTextBottomCalculated={setTextBottom} // Передаем нижний край текста
            />
          </div>

          {/* Dropdown Menu */}
          {isDropdownOpen && (
            <DropdownMenu
              ref={dropdownRef}
              onClose={closeDropdown}
              topOffset={textBottom} // Используем нижний край текста
            />
          )}

          {/* Content */}
          <div className="flex-1 overflow-y-auto px-4"> {/* Убрал padding сверху и снизу, оставил по бокам */}
            <SidebarContent />
          </div>

          {/* Footer */}
          <div className="px-4 pb-4"> {/* Убрал padding сверху, оставил снизу и по бокам */}
            <SidebarFooter
              analytics={{ current: 15, max: 250 }} // Передаем данные для аналитики
              tokens={{ current: 2200, max: 150000 }} // Передаем данные для токенов
            />
          </div>
        </div>
      ) : (
        <CollapsedSidebar
          toggleSidebar={toggleSidebar}
          isCollapsed={isCollapsed}
        />
      )}
    </motion.aside>
  );
};