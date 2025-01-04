import { useRef, useEffect } from 'react';

interface SidebarHeaderProps {
  isDropdownOpen: boolean;
  toggleDropdown: () => void;
  toggleSidebar: () => void;
  isHovered: boolean;
  setIsHovered: (hovered: boolean) => void;
  onTextBottomCalculated: (bottom: number) => void; // Колбэк для передачи нижнего края текста
  isCollapsed: boolean; // Добавляем пропс для определения состояния сайдбара
}

export const SidebarHeader = ({
  isDropdownOpen,
  toggleDropdown,
  toggleSidebar,
  isHovered,
  setIsHovered,
  onTextBottomCalculated,
  isCollapsed, // Получаем состояние сайдбара
}: SidebarHeaderProps) => {
  const textRef = useRef<HTMLHeadingElement>(null);

  useEffect(() => {
    if (textRef.current) {
      // Получаем координаты нижнего края текста
      const textRect = textRef.current.getBoundingClientRect();
      onTextBottomCalculated(textRect.bottom);
    }
  }, [onTextBottomCalculated]);

  return (
    <div className="flex items-center space-x-3 relative">
      {/* Логотип */}
      <button onClick={toggleDropdown} className="focus:outline-none">
        <div className="w-8 h-8 bg-blue-500 rounded-full flex items-center justify-center text-white font-bold text-lg">
          SW
        </div>
      </button>

      {/* Название */}
      <button onClick={toggleDropdown} className="focus:outline-none">
        <h1 ref={textRef} className="text-xl font-bold">Support's Workspace</h1>
      </button>

      {/* Стрелка вниз для выпадающего меню */}
      <button onClick={toggleDropdown} className="focus:outline-none">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className={`h-5 w-5 transition-transform ${isDropdownOpen ? 'rotate-180' : ''}`}
          viewBox="0 0 20 20"
          fill="currentColor"
        >
          <path
            fillRule="evenodd"
            d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
            clipRule="evenodd"
          />
        </svg>
      </button>

      {/* Кнопка для сворачивания панели */}
      {!isCollapsed && ( // Отображаем кнопку только если сайдбар развернут
        <button
          onClick={toggleSidebar}
          onMouseEnter={() => setIsHovered(true)}
          onMouseLeave={() => setIsHovered(false)}
          className="p-1 rounded hover:bg-gray-200 transition-colors ml-2"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-5 w-5"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M15 19l-7-7 7-7"
            />
          </svg>
        </button>
      )}
    </div>
  );
};