import { FaPlus } from 'react-icons/fa';

interface SidebarFooterProps {
  analytics: { current: number; max: number };
  tokens: { current: number; max: number };
}

// Функция для форматирования чисел
const formatNumber = (num: number): string => {
  if (num >= 1000) {
    return `${(num / 1000).toFixed(1).replace('.', ',')}к`; // Заменяем точку на запятую
  }
  return num.toString();
};

export const SidebarFooter = ({ analytics, tokens }: SidebarFooterProps) => {
  return (
    <div className="mt-auto space-y-4">
      {/* Два столбца с текстом и прогресс-барами */}
      <div className="grid grid-cols-2 gap-4">
        {/* Первый столбец: Аналитики */}
        <div>
          <p className="text-sm text-gray-600">Аналитики</p>
          <p className="text-lg font-semibold">
            {analytics.current}/{analytics.max}
          </p>
          <div className="w-full bg-gray-200 rounded-full h-2">
            <div
              className="bg-blue-500 h-2 rounded-full"
              style={{ width: `${(analytics.current / analytics.max) * 100}%` }}
            ></div>
          </div>
        </div>

        {/* Второй столбец: Токены */}
        <div>
          <p className="text-sm text-gray-600">Токены</p>
          <p className="text-lg font-semibold">
            {formatNumber(tokens.current)}/{formatNumber(tokens.max)}
          </p>
          <div className="w-full bg-gray-200 rounded-full h-2">
            <div
              className="bg-green-500 h-2 rounded-full"
              style={{ width: `${(tokens.current / tokens.max) * 100}%` }}
            ></div>
          </div>
        </div>
      </div>

      {/* Кнопка "Перейти на плюс" */}
      <button
        className="w-full flex items-center justify-center space-x-2 bg-blue-500 text-white p-2 rounded-lg hover:bg-blue-600 transition-colors"
      >
        <FaPlus className="text-lg" />
        <span>Перейти на плюс</span>
      </button>
    </div>
  );
};