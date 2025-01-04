import { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // Добавлен useNavigate
import {
  FaClock,
  FaPlusCircle,
  FaFolder,
  FaUserPlus,
  FaCopy,
  FaUpload,
  FaEdit,
  FaTrash,
  FaEllipsisV,
} from 'react-icons/fa';

export const SidebarContent = () => {
  const [isMenuOpen, setIsMenuOpen] = useState<number | null>(null);
  const menuRef = useRef<HTMLDivElement>(null);
  const navigate = useNavigate(); // Хук для навигации

  const toggleMenu = (index: number) => {
    setIsMenuOpen(isMenuOpen === index ? null : index);
  };

  // Закрытие меню при клике вне его области
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (menuRef.current && !menuRef.current.contains(event.target as Node)) {
        setIsMenuOpen(null);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const projects = [
    { id: 1, name: 'Project Alpha' },
    { id: 2, name: 'Project Beta' },
    { id: 3, name: 'Project Gamma' },
  ];

  return (
    <div className="p-4">
      {/* Верхняя часть меню */}
      <div className="space-y-4">
        <button
          onClick={() => navigate('/projects')} // Переход на страницу проектов
          className="flex items-center space-x-3 w-full text-left"
        >
          <FaClock className="text-gray-500" />
          <span>Недавние проекты</span>
        </button>
        <button
          onClick={() => navigate('/projects/new')} // Переход на страницу нового проекта
          className="flex items-center space-x-3 w-full text-left"
        >
          <FaPlusCircle className="text-gray-500" />
          <span>Новый проект</span>
        </button>
        <button
          onClick={() => navigate('/settings/users')} // Переход на страницу настроек команды
          className="flex items-center space-x-3 w-full text-left"
        >
          <FaUserPlus className="text-gray-500" />
          <span>Настройки команды</span>
        </button>
      </div>

      {/* Разделитель */}
      <div className="border-t border-gray-200 my-4"></div>

      {/* Надпись "Все проекты" */}
      <h2 className="text-lg font-semibold mb-4">Все проекты</h2>

      {/* Список проектов */}
      <div className="space-y-2">
        {projects.map((project, index) => (
          <div key={project.id} className="flex items-center justify-between p-2 hover:bg-gray-100 rounded">
            <div className="flex items-center space-x-3">
              <FaFolder className="text-gray-500" />
              <span>{project.name}</span>
            </div>
            <div className="relative" ref={menuRef}>
              <button
                onClick={() => toggleMenu(index)}
                className="p-1 rounded hover:bg-gray-200"
              >
                <FaEllipsisV className="text-gray-500" />
              </button>
              {isMenuOpen === index && (
                <div className="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg py-2 z-10">
                  <button className="w-full px-4 py-2 text-left hover:bg-gray-100 flex items-center space-x-2">
                    <FaCopy className="text-gray-500" />
                    <span>Копировать ID проекта</span>
                  </button>
                  <button className="w-full px-4 py-2 text-left hover:bg-gray-100 flex items-center space-x-2">
                    <FaUpload className="text-gray-500" />
                    <span>Загрузить картинку</span>
                  </button>
                  <button className="w-full px-4 py-2 text-left hover:bg-gray-100 flex items-center space-x-2">
                    <FaEdit className="text-gray-500" />
                    <span>Переименовать проект</span>
                  </button>
                  <button className="w-full px-4 py-2 text-left hover:bg-gray-100 flex items-center space-x-2 text-red-500">
                    <FaTrash className="text-red-500" />
                    <span>Удалить проект</span>
                  </button>
                </div>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};