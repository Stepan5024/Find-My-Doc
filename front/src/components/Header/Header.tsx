import { motion } from 'framer-motion';
import { FaQuestionCircle, FaBell, FaPlus } from 'react-icons/fa'; // Импорт иконок

interface HeaderProps {
  isSidebarCollapsed: boolean;
  toggleSidebar: () => void;
  currentSection: string; // Пропс для текущего раздела
}

export const Header = ({ isSidebarCollapsed, toggleSidebar, currentSection }: HeaderProps) => {
  return (
    <motion.header
      className="bg-gray-100 shadow p-4 flex justify-between items-center" // Изменено на justify-between
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 0.5 }}
      style={{
        width: `calc(100% - ${isSidebarCollapsed ? '4rem' : '16rem'})`, // Ширина зависит от состояния Sidebar
        marginLeft: isSidebarCollapsed ? '4rem' : '16rem', // Отступ слева
      }}
    >
      {/* Левая часть: Надпись текущего раздела */}
      <div className="flex items-center">
        <h1 className="text-xl font-semibold">{currentSection}</h1> {/* Динамическая надпись */}
      </div>

      {/* Правая часть: Кнопки */}
      <div className="flex items-center space-x-4">
        {/* Кнопка "Новый проект" */}
        <button className="flex items-center space-x-2 bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition-colors">
          <FaPlus className="text-lg" />
          <span>Новый проект</span>
        </button>

        {/* Колокольчик для уведомлений */}
        <button className="relative p-2 rounded-full hover:bg-gray-200 transition-colors">
          <FaBell className="text-xl text-gray-700" />
          {/* Бейдж для уведомлений (опционально) */}
          <span className="absolute top-0 right-0 bg-red-500 text-white text-xs rounded-full px-1.5 py-0.5">
            3
          </span>
        </button>

        {/* Кнопка "Помощь" */}
        <button className="flex items-center space-x-2 p-2 rounded-full hover:bg-gray-200 transition-colors">
          <FaQuestionCircle className="text-xl text-gray-700" />
          <span>Помощь</span>
        </button>
      </div>
    </motion.header>
  );
};