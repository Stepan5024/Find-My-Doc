import { useState } from 'react';
import { FaCog, FaChartLine, FaProjectDiagram } from 'react-icons/fa';

export const ProjectsPage = () => {
  const [selectedProject, setSelectedProject] = useState<number | null>(null);

  // Функция для определения времени суток
  const getGreeting = () => {
    const hour = new Date().getHours();
    if (hour < 12) return 'Good morning!';
    if (hour < 18) return 'Good afternoon!';
    return 'Good evening!';
  };

  // Данные для карточек проектов
  const projects = [
    { id: 1, name: 'Untitled Project', propertiesCount: 2 },
    { id: 2, name: 'Untitled Project', propertiesCount: 3 },
    { id: 3, name: 'Untitled Project', propertiesCount: 1 },
  ];

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      {/* Приветствие и информация о проектах */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">{getGreeting()}</h1>
        <p className="text-lg text-gray-600">3 projects</p>
      </div>
      {/* Разделы свойств с картинками (иконками) */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        {/* Basic Properties */}
        <div className="bg-white p-6 rounded-lg shadow-sm flex items-start">
          <div className="text-blue-500 mr-4">
            <FaCog className="w-8 h-8" /> {/* Иконка для Basic Properties */}
          </div>
          <div>
            <h2 className="text-xl font-semibold text-gray-900 mb-2">Basic Properties</h2>
            <p className="text-gray-600">Configure basic settings for your project.</p>
          </div>
        </div>

        {/* Advanced Properties */}
        <div className="bg-white p-6 rounded-lg shadow-sm flex items-start">
          <div className="text-green-500 mr-4">
            <FaChartLine className="w-8 h-8" /> {/* Иконка для Advanced Properties */}
          </div>
          <div>
            <h2 className="text-xl font-semibold text-gray-900 mb-2">Advanced Properties</h2>
            <p className="text-gray-600">Set up advanced configurations and integrations.</p>
          </div>
        </div>

        {/* Workflows */}
        <div className="bg-white p-6 rounded-lg shadow-sm flex items-start">
          <div className="text-purple-500 mr-4">
            <FaProjectDiagram className="w-8 h-8" /> {/* Иконка для Workflows */}
          </div>
          <div>
            <h2 className="text-xl font-semibold text-gray-900 mb-2">Workflows</h2>
            <p className="text-gray-600">Define workflows and automation rules.</p>
          </div>
        </div>
      </div>

      <div className="mb-8">
        {/* Разделительная линия */}
        <hr className="border-t border-gray-300 my-6 mx-auto w-11/12 max-w-4xl" />
      </div>

      {/* Список проектов */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {projects.map((project) => (
          <div
            key={project.id}
            className={`bg-white p-6 rounded-lg shadow-sm cursor-pointer transition-all ${selectedProject === project.id ? 'border-2 border-blue-500' : 'border border-gray-200'
              }`}
            onClick={() => setSelectedProject(project.id)}
          >
            <h3 className="text-xl font-semibold text-gray-900 mb-2">{project.name}</h3>
            <p className="text-gray-600">{project.propertiesCount} properties</p>
          </div>
        ))}
      </div>


    </div>
  );
};