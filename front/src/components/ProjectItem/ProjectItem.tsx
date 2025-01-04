import { motion } from 'framer-motion';

export const ProjectItem = ({ name, properties }: { name: string; properties: number }) => {
  return (
    <motion.div
      className="p-4 border rounded-lg hover:shadow-lg transition-shadow"
      whileHover={{ scale: 1.05 }}
    >
      <h3 className="text-lg font-semibold">{name}</h3>
      <p className="text-gray-600">{properties} properties</p>
    </motion.div>
  );
};